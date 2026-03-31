package security.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Central audit log for the security system.
 *
 * Stores all access control events in memory and appends each entry to
 * access_log.txt so the log survives after the program exits.
 *
 * -----------------------------------------------------------------------
 * DESIGN PATTERN: Singleton
 * -----------------------------------------------------------------------
 * AuditLog uses the Singleton pattern to ensure there is exactly one log
 * across the entire application. Two reasons:
 *
 *   1. Consistency — if multiple instances existed, entries from different
 *      parts of the system could end up in different logs, making it
 *      impossible to get a complete picture of what happened.
 *
 *   2. File integrity — only one instance holds the file writer, so there
 *      is no risk of two instances corrupting the file simultaneously.
 *
 * The Singleton uses the initialisation-on-demand holder idiom. The
 * instance is created lazily when first requested, and the JVM guarantees
 * that class loading is thread-safe — no synchronisation needed.
 * -----------------------------------------------------------------------
 *
 * JAVA GENERICS — generic filter method
 * -----------------------------------------------------------------------
 * The filter(Predicate<LogEntry>) method lets callers filter entries by
 * any field without a separate method for each field. For example:
 *
 *   log.filter(e -> e.getResult() == AccessResult.ALLOW)
 *   log.filter(e -> e.getUserId().equals("user1"))
 *   log.filter(e -> e.getOperation() == Operation.WRITE)
 *
 * Predicate<LogEntry> is a functional interface from java.util.function,
 * making this compatible with lambda expressions.
 * -----------------------------------------------------------------------
 */
public class AuditLog implements AccessLogger {

    private static final String LOG_FILE = "access_log.txt";

    private final List<LogEntry> entries = new ArrayList<>();
    private final PrintWriter    fileWriter;

    // -----------------------------------------------------------------------
    // Singleton — initialisation-on-demand holder idiom
    // -----------------------------------------------------------------------

    private AuditLog() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE, true)));
        } catch (IOException e) {
            System.err.println("[AuditLog] Warning: could not open log file. File logging disabled.");
            writer = null;
        }
        this.fileWriter = writer;
    }

    private static class Holder {
        private static final AuditLog INSTANCE = new AuditLog();
    }

    /** Returns the single AuditLog instance for this application. */
    public static AuditLog getInstance() {
        return Holder.INSTANCE;
    }

    // -----------------------------------------------------------------------
    // Logging
    // -----------------------------------------------------------------------

    /**
     * Records an access control event.
     * Stores it in memory and appends it to access_log.txt.
     */
    @Override
    public void log(LogEntry entry) {
        if (entry == null) throw new IllegalArgumentException("LogEntry must not be null.");
        entries.add(entry);
        if (fileWriter != null) {
            fileWriter.println(entry.toString());
            fileWriter.flush();
        }
    }

    // -----------------------------------------------------------------------
    // Querying
    // -----------------------------------------------------------------------

    /** Returns an unmodifiable view of all log entries. */
    public List<LogEntry> getAll() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Returns all entries matching the given predicate.
     *
     * JAVA GENERICS: Uses Predicate<LogEntry> so any field can be used
     * as a filter criterion without writing a separate method for each.
     *
     * @param predicate a condition to test each entry against
     * @return a new list of entries that satisfy the predicate
     */
    public List<LogEntry> filter(Predicate<LogEntry> predicate) {
        if (predicate == null) throw new IllegalArgumentException("Predicate must not be null.");
        List<LogEntry> result = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (predicate.test(entry)) {
                result.add(entry);
            }
        }
        return result;
    }

    /** Convenience — returns all entries for a specific user ID. */
    public List<LogEntry> filterByUser(String userId) {
        return filter(e -> e.getUserId().equals(userId));
    }

    /** Convenience — returns all entries with a specific result. */
    public List<LogEntry> filterByResult(AccessResult result) {
        return filter(e -> e.getResult() == result);
    }

    /** Returns the total number of entries in the log. */
    public int size() {
        return entries.size();
    }

    /**
     * Clears all in-memory entries.
     * Used in tests to reset state between test runs.
     * Does NOT clear the file.
     */
    public void clearForTesting() {
        entries.clear();
    }

    /** Prints all log entries to System.out. */
    public void printAll() {
        if (entries.isEmpty()) {
            System.out.println("[AuditLog] No entries recorded.");
            return;
        }
        System.out.println("\n========== AUDIT LOG ==========");
        for (LogEntry entry : entries) {
            System.out.println(entry);
        }
        System.out.println("================================\n");
    }
}

package security.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class AuditLog implements AccessLogger {

    private static final String LOG_FILE = "access_log.txt";

    private final List<LogEntry> entries = new ArrayList<>();
    private final PrintWriter fileWriter;

    // singleton initialisation ondemand holder idiom


    private AuditLog() {
        PrintWriter writer;
        try {
            // append=true so the file grows across multiple runs
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

    /**
     * Returns the single Auditlog instance for this application
     */
    public static AuditLog getInstance() {
        return Holder.INSTANCE;
    }

    // Logging

    @Override
    public void log(LogEntry entry) {
        if (entry == null) throw new IllegalArgumentException("LogEntry must not be null.");
        entries.add(entry);
        if (fileWriter != null) {
            fileWriter.println(entry.toString());
            fileWriter.flush();
        }
    }

    // Querying


    /**
     * Returns an unmodifiable view of all log entries
     */
    public List<LogEntry> getAll() {
        return Collections.unmodifiableList(entries);
    }

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

    /**
     * returns all entries for a specific user ID
     */
    public List<LogEntry> filterByUser(String userId) {
        return filter(e -> e.getUserId().equals(userId));
    }

    /**
     * Convenience method  returns all entries with a specific result
     */
    public List<LogEntry> filterByResult(AccessResult result) {
        return filter(e -> e.getResult() == result);
    }

    /**
     * Returns the total num of entries in the log
     */
    public int size() {
        return entries.size();
    }

    public void clearForTesting() {
        entries.clear();
    }

    /**
     * Prints all log entries
     */
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
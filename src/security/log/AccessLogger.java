package security.log;

/**
 * Interface for logging access control events.
 *
 * AccessController depends on this interface, not on AuditLog directly.
 * This means the logging implementation can be swapped (e.g. for a mock
 * in tests) without changing AccessController — an application of the
 * Dependency Inversion Principle.
 */
public interface AccessLogger {

    /**
     * Records a single access control event.
     *
     * @param entry the event to record — must not be null
     */
    void log(LogEntry entry);
}

package security.log;

/*
 * Interface for logging access control events
 */
public interface AccessLogger {

    void log(LogEntry entry);
}

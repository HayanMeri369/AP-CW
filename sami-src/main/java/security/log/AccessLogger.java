package security.log;

public interface AccessLogger {

    /**
     * Records a single access control event
     */
    void log(LogEntry entry);
}
package security.model;

/**
 * The two operations that can be requested on a protected resource.
 * Used by AccessPolicy, LogEntry, and the capability factory.
 */
public enum Operation {
    READ,
    WRITE
}

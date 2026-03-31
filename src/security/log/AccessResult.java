package security.log;

/**
 * Represents the outcome of an access control decision.
 *
 * Used by LogEntry to record whether access was granted or denied,
 * and by AuditLog to allow filtering entries by outcome.
 *
 * Using an enum instead of a boolean ensures every component uses the
 * same type for access decisions, catching mismatches at compile time.
 */
public enum AccessResult {
    ALLOW,
    REFUSE
}

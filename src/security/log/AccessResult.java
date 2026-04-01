package security.log;

/*
 * represents the outcome of an access control decision
 used by logEntry to record whether access was granted or denied

 */
public enum AccessResult {
    ALLOW,
    REFUSE
}

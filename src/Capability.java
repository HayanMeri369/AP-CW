import java.time.LocalDateTime;

/**
 * Represents a time-stamped permission granted to a user for a specific resource.
 */
public interface Capability<P extends Permission, R extends Resource<?>> {
    User getUser();
    R getResource();
    P getPermission();
    LocalDateTime getIssuedAt();
}
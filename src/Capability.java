import java.time.LocalDateTime;
public interface Capability<P extends Permission, R extends Resource<?>> {
    User getUser();
    R getResource();
    P getPermission();
    LocalDateTime getIssuedAt();
}
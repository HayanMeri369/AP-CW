import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractCapability<P extends Permission, R extends Resource<?>> 
        implements Capability<P, R> {
    private final User user;
    private final R resource;
    private final P permission;
    private final LocalDateTime issuedAt;

    protected AbstractCapability(User user, R resource, P permission) {
        this.user = Objects.requireNonNull(user);
        this.resource = Objects.requireNonNull(resource);
        this.permission = Objects.requireNonNull(permission);
        this.issuedAt = LocalDateTime.now();
    }

    @Override public User getUser() { return user; }
    @Override public R getResource() { return resource; }
    @Override public P getPermission() { return permission; }
    @Override public LocalDateTime getIssuedAt() { return issuedAt; }
}
package security.capability;

import security.model.Resource;
import security.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Base implementation of Capability that stores the user, resource,
 * permission, and the timestamp at which the capability was issued.
 *
 * Concrete subclasses (ReadCapability, WriteCapability, ReadWriteCapability)
 * only need to supply the correct permission object via the constructor.
 */
public abstract class AbstractCapability<P extends Permission, R extends Resource<?>>
        implements Capability<P, R> {

    private final User          user;
    private final R             resource;
    private final P             permission;
    private final LocalDateTime issuedAt;

    protected AbstractCapability(User user, R resource, P permission) {
        this.user       = Objects.requireNonNull(user,       "User must not be null.");
        this.resource   = Objects.requireNonNull(resource,   "Resource must not be null.");
        this.permission = Objects.requireNonNull(permission, "Permission must not be null.");
        this.issuedAt   = LocalDateTime.now();
    }

    @Override public User          getUser()       { return user; }
    @Override public R             getResource()   { return resource; }
    @Override public P             getPermission() { return permission; }
    @Override public LocalDateTime getIssuedAt()   { return issuedAt; }
}

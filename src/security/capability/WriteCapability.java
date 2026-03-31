package security.capability;

import security.model.Resource;
import security.model.User;

/** Concrete capability granting write-only access to a resource. */
public final class WriteCapability<R extends Resource<?>>
        extends AbstractCapability<WriteAccess, R> {

    public WriteCapability(User user, R resource) {
        super(user, resource, WritePermission.getInstance());
    }
}

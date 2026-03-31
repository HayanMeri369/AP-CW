package security.capability;

import security.model.Resource;
import security.model.User;

/** Concrete capability granting both read and write access to a resource. */
public final class ReadWriteCapability<R extends Resource<?>>
        extends AbstractCapability<ReadWritePermission, R> {

    public ReadWriteCapability(User user, R resource) {
        super(user, resource, ReadWritePermission.getInstance());
    }
}

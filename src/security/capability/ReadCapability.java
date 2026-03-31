package security.capability;

import security.model.Resource;
import security.model.User;

/** Concrete capability granting read-only access to a resource. */
public final class ReadCapability<R extends Resource<?>>
        extends AbstractCapability<ReadAccess, R> {

    public ReadCapability(User user, R resource) {
        super(user, resource, ReadPermission.getInstance());
    }
}

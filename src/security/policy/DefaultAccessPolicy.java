package security.policy;

import security.model.AccessScope;
import security.model.Operation;
import security.model.Resource;
import security.model.Role;
import security.model.User;

/**
 * Default role-based access control policy.
 *
 * DESIGN PATTERN: Strategy (concrete strategy)
 * This is the standard implementation of AccessPolicy. It encodes the
 * following rules:
 *
 *   GUEST   → PUBLIC resources, READ only
 *   STUDENT → PUBLIC + INTERNAL resources, READ only
 *   STAFF   → PUBLIC + INTERNAL resources, READ and WRITE
 *   ADMIN   → all resources (PUBLIC, INTERNAL, CONFIDENTIAL), READ and WRITE
 */
public class DefaultAccessPolicy implements AccessPolicy {

    @Override
    public boolean isAllowed(User user, Resource<?> resource, Operation operation) {
        if (user == null || resource == null || operation == null) return false;

        Role        role  = user.getRole();
        AccessScope scope = (AccessScope) resource.getScope();

        switch (role) {
            case ADMIN:
                // Admin can do anything on any resource
                return true;

            case STAFF:
                // Staff can read and write PUBLIC and INTERNAL resources only
                return scope == AccessScope.PUBLIC || scope == AccessScope.INTERNAL;

            case STUDENT:
                // Students can only read PUBLIC and INTERNAL resources
                return operation == Operation.READ
                        && (scope == AccessScope.PUBLIC || scope == AccessScope.INTERNAL);

            case GUEST:
                // Guests can only read PUBLIC resources
                return operation == Operation.READ
                        && scope == AccessScope.PUBLIC;

            default:
                return false;
        }
    }
}

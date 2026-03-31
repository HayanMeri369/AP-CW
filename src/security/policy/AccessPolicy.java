package security.policy;

import security.model.Operation;
import security.model.Resource;
import security.model.User;

/**
 * Strategy interface for access control rules.
 *
 * DESIGN PATTERN: Strategy
 * AccessPolicy is the strategy interface. Different implementations can
 * encode different rule sets (e.g. DefaultAccessPolicy, a stricter policy
 * for exams, or a permissive test policy). AccessController holds a
 * reference to this interface and delegates all decisions to it, meaning
 * the policy can be swapped at runtime without changing AccessController.
 */
public interface AccessPolicy {

    /**
     * Returns true if the user is permitted to perform the operation
     * on the given resource under this policy.
     *
     * @param user      the user requesting access
     * @param resource  the resource being accessed
     * @param operation READ or WRITE
     * @return true if access is permitted, false otherwise
     */
    boolean isAllowed(User user, Resource<?> resource, Operation operation);
}

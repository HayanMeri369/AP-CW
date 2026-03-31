package security.policy;

import security.log.AccessLogger;
import security.log.AccessResult;
import security.log.LogEntry;
import security.model.Operation;
import security.model.Resource;
import security.model.User;

/**
 * Central entry point for all access control requests.
 *
 * DESIGN PATTERN: Strategy (context)
 * AccessController is the context class in the Strategy pattern. It holds
 * a reference to an AccessPolicy (the strategy) and delegates every
 * allow/deny decision to it. The policy can be swapped at construction
 * time without changing any of AccessController's logic.
 *
 * AccessController also depends on AccessLogger (not a concrete logger),
 * applying the Dependency Inversion Principle — it logs through the
 * interface, so the logging implementation can be swapped independently.
 *
 * Usage:
 *   AccessController ctrl = new AccessController(new DefaultAccessPolicy(),
 *                                                AuditLog.getInstance());
 *   AccessResult result = ctrl.request(user, resource, Operation.READ);
 */
public class AccessController {

    private final AccessPolicy policy;
    private final AccessLogger logger;

    public AccessController(AccessPolicy policy, AccessLogger logger) {
        if (policy == null) throw new IllegalArgumentException("Policy must not be null.");
        if (logger == null) throw new IllegalArgumentException("Logger must not be null.");
        this.policy = policy;
        this.logger = logger;
    }

    /**
     * Evaluates whether the user may perform the operation on the resource,
     * logs the decision, and returns the result.
     *
     * @param user      the user requesting access
     * @param resource  the resource being accessed
     * @param operation READ or WRITE
     * @return ALLOW if the policy permits it, REFUSE otherwise
     */
    public AccessResult request(User user, Resource<?> resource, Operation operation) {
        if (user == null || resource == null || operation == null) {
            throw new IllegalArgumentException("User, resource, and operation must not be null.");
        }

        boolean allowed = policy.isAllowed(user, resource, operation);
        AccessResult result = allowed ? AccessResult.ALLOW : AccessResult.REFUSE;

        logger.log(new LogEntry(
                user.getId(),
                user.getRole(),
                resource.getName(),
                operation,
                result
        ));

        return result;
    }
}

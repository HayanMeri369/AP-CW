package security.capability;

import security.model.Operation;
import security.model.Resource;
import security.model.User;
import security.policy.AccessPolicy;

import java.util.Optional;

/**
 * Concrete CapabilityFactory that consults an AccessPolicy before issuing
 * any capability.
 *
 * DESIGN PATTERN: Factory
 * This class is the concrete factory. It receives an AccessPolicy (the
 * Strategy) at construction time and delegates all allow/deny decisions
 * to it. This keeps the capability-issuing logic separate from the
 * policy rules, so either can change independently.
 */
public class PolicyBasedCapabilityFactory implements CapabilityFactory {

    private final AccessPolicy policy;

    public PolicyBasedCapabilityFactory(AccessPolicy policy) {
        if (policy == null) throw new IllegalArgumentException("Policy must not be null.");
        this.policy = policy;
    }

    @Override
    public <R extends Resource<?>> Optional<Capability<? extends ReadAccess, R>>
            issueReadCapability(User user, R resource) {
        if (!policy.isAllowed(user, resource, Operation.READ)) {
            return Optional.empty();
        }
        return Optional.of(new ReadCapability<>(user, resource));
    }

    @Override
    public <R extends Resource<?>> Optional<Capability<? extends WriteAccess, R>>
            issueWriteCapability(User user, R resource) {
        if (!policy.isAllowed(user, resource, Operation.WRITE)) {
            return Optional.empty();
        }
        return Optional.of(new WriteCapability<>(user, resource));
    }

    @Override
    public <R extends Resource<?>> Optional<Capability<ReadWritePermission, R>>
            issueReadWriteCapability(User user, R resource) {
        boolean canRead  = policy.isAllowed(user, resource, Operation.READ);
        boolean canWrite = policy.isAllowed(user, resource, Operation.WRITE);
        if (!canRead || !canWrite) {
            return Optional.empty();
        }
        return Optional.of(new ReadWriteCapability<>(user, resource));
    }
}

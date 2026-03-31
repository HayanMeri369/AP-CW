import java.util.Optional;

public class PolicyBasedCapabilityFactory implements CapabilityFactory {
    private final AccessPolicy policy;

    public PolicyBasedCapabilityFactory(AccessPolicy policy) {
        this.policy = policy;
    }

    @Override
    public <R extends Resource<?>> Optional<Capability<? extends ReadAccess, R>> issueReadCapability(User user, R resource) {
        if (!policy.isAllowed(user, resource, AccessOperation.READ)) return Optional.empty();
        return Optional.of(new ReadCapability<>(user, resource));
    }

    @Override
    public <R extends Resource<?>> Optional<Capability<? extends WriteAccess, R>> issueWriteCapability(User user, R resource) {
        // Write capability issuance is not implemented yet.
        return Optional.empty(); 
    }
}
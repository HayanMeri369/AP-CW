import java.util.Optional;
public interface CapabilityFactory {
    <R extends Resource<?>> Optional<Capability<? extends ReadAccess, R>> issueReadCapability(User user, R resource);
    <R extends Resource<?>> Optional<Capability<? extends WriteAccess, R>> issueWriteCapability(User user, R resource);
}
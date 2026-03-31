import java.util.Optional;

/**
 * Produces capabilities only when the configured access policy approves the request.
 */
public interface CapabilityFactory {
    /** Issues a read capability for the given user and resource when allowed. */
    <R extends Resource<?>> Optional<Capability<? extends ReadAccess, R>> issueReadCapability(User user, R resource);

    /** Issues a write capability for the given user and resource when allowed. */
    <R extends Resource<?>> Optional<Capability<? extends WriteAccess, R>> issueWriteCapability(User user, R resource);
}
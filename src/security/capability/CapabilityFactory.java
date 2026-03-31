package security.capability;

import security.model.Resource;
import security.model.User;

import java.util.Optional;

/**
 * Factory interface for producing typed Capability objects.
 *
 * DESIGN PATTERN: Factory
 * The factory decides which capability to issue (or whether to issue one
 * at all) based on the access policy. Callers never construct capability
 * objects directly — they ask the factory, which encapsulates the decision
 * logic. The interface also allows swapping implementations (e.g. a mock
 * factory in tests) without changing any calling code.
 *
 * JAVA GENERICS: Each method is itself generic on R extends Resource<?>.
 * This means issueReadCapability(user, examPaper) returns an
 * Optional<Capability<ReadAccess, ExamPaper>>, preserving the exact
 * resource type through the return value so no casting is needed.
 */
public interface CapabilityFactory {

    /**
     * Issues a read capability if the policy allows it.
     *
     * @param user     the user requesting access
     * @param resource the resource being accessed
     * @param <R>      the exact resource type
     * @return an Optional containing the capability, or empty if denied
     */
    <R extends Resource<?>> Optional<Capability<? extends ReadAccess, R>>
        issueReadCapability(User user, R resource);

    /**
     * Issues a write capability if the policy allows it.
     *
     * @param user     the user requesting access
     * @param resource the resource being accessed
     * @param <R>      the exact resource type
     * @return an Optional containing the capability, or empty if denied
     */
    <R extends Resource<?>> Optional<Capability<? extends WriteAccess, R>>
        issueWriteCapability(User user, R resource);

    /**
     * Issues a read-write capability if the policy allows both operations.
     *
     * @param user     the user requesting access
     * @param resource the resource being accessed
     * @param <R>      the exact resource type
     * @return an Optional containing the capability, or empty if denied
     */
    <R extends Resource<?>> Optional<Capability<ReadWritePermission, R>>
        issueReadWriteCapability(User user, R resource);
}

/**
 * Defines the rule set used to decide whether a user can perform an operation on a resource.
 */
public interface AccessPolicy {
    boolean isAllowed(User user, Resource<?> resource, AccessOperation operation);
}
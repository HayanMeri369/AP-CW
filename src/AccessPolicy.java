public interface AccessPolicy {
    boolean isAllowed(User user, Resource<?> resource, AccessOperation operation);
}
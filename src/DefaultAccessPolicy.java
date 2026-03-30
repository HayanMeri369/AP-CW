public class DefaultAccessPolicy implements AccessPolicy {
    @Override
    public boolean isAllowed(User user, Resource<?> resource, AccessOperation operation) {
        if (user == null || resource == null) return false;
        Role role = user.getRole();
        AccessScope scope = (AccessScope) resource.getScope();

        if (role == Role.ADMIN) return true;
        if (role == Role.STUDENT && scope != AccessScope.CONFIDENTIAL && operation == AccessOperation.READ) return true;
        if (role == Role.GUEST && scope == AccessScope.PUBLIC && operation == AccessOperation.READ) return true;
        
        return false;
    }
}
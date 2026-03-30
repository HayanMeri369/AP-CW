public final class ReadCapability<R extends Resource<?>> extends AbstractCapability<ReadAccess, R> {
    public ReadCapability(User user, R resource) {
        super(user, resource, ReadPermission.getInstance());
    }
}
package security.capability;

/** Singleton permission object representing read-only access. */
public final class ReadPermission implements ReadAccess {
    private static final ReadPermission INSTANCE = new ReadPermission();
    private ReadPermission() {}
    public static ReadPermission getInstance() { return INSTANCE; }
}

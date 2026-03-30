public final class ReadPermission implements ReadAccess {
    private static final ReadPermission INSTANCE = new ReadPermission();
    private ReadPermission() {}
    public static ReadPermission getInstance() { return INSTANCE; }
}
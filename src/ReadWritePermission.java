public final class ReadWritePermission implements ReadAccess, WriteAccess {
    private static final ReadWritePermission INSTANCE = new ReadWritePermission();
    private ReadWritePermission() {}
    public static ReadWritePermission getInstance() { return INSTANCE; }
}
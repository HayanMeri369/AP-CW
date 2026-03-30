public final class WritePermission implements WriteAccess {
    private static final WritePermission INSTANCE = new WritePermission();
    private WritePermission() {}
    public static WritePermission getInstance() { return INSTANCE; }
}
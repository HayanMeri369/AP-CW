public abstract class Resource<S> {
    private String name;
    private S scope;

    public Resource(String name, S scope) {
        this.name = name;
        this.scope = scope;
    }

    public String getname() { return name; }
    public S getScope() { return scope; }
}
package security.model;

/**
 * Generic base type for all resources protected by an access scope.
 *
 * JAVA GENERICS: Resource<S extends AccessScope> is typed on its scope
 * so that the compiler enforces that only valid AccessScope values are
 * ever assigned to a resource. Concrete subclasses declare their scope
 * type explicitly, e.g. Resource<AccessScope>.
 */
public abstract class Resource<S extends AccessScope> {

    private final String name;
    private final S scope;

    public Resource(String name, S scope) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Resource name must not be null or blank.");
        if (scope == null)
            throw new IllegalArgumentException("Resource scope must not be null.");
        this.name  = name;
        this.scope = scope;
    }

    public String getName()  { return name; }
    public S      getScope() { return scope; }

    @Override
    public String toString() { return name + " [" + scope + "]"; }
}

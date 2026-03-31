package security.model;

/**
 * Represents a system user with a unique ID and an assigned role.
 *
 * The constructor is package-private so that User objects can only be
 * created through UserBuilder, enforcing validated construction.
 */
public class User {

    private final String id;
    private final Role   role;

    /** Package-private — use UserBuilder to create instances. */
    User(String id, Role role) {
        this.id   = id;
        this.role = role;
    }

    public String getId()   { return id; }
    public Role   getRole() { return role; }

    @Override
    public String toString() { return id + " [" + role + "]"; }
}

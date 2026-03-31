/**
 * Builder for creating validated {@code User} instances.
 */
public class UserBuilder {
    private String id;
    private Role role;

    public UserBuilder setID(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public User build() {
        if (id == null || role == null) {
            throw new IllegalStateException("User must have id and role");
        }
        return new User(id, role);
    }
}
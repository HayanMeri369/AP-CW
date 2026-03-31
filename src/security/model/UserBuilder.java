package security.model;

/**
 * Builder for creating validated User instances.
 *
 * DESIGN PATTERN: Builder
 * Ensures a User can only be constructed with a valid ID and role.
 * Calling build() without setting both fields throws an exception,
 * catching the error at the point of construction rather than later.
 *
 * Usage:
 *   User admin = new UserBuilder().setId("u1").setRole(Role.ADMIN).build();
 */
public class UserBuilder {

    private String id;
    private Role   role;

    public UserBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public User build() {
        if (id == null || id.isBlank())
            throw new IllegalStateException("User must have a non-blank ID.");
        if (role == null)
            throw new IllegalStateException("User must have a role.");
        return new User(id, role);
    }
}

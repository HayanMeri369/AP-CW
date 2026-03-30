public class User {
    private String id;
    private Role role;

    public User(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    public String getId() { return id; }
    public Role getRole() { return role; }
}
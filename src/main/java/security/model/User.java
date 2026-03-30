package security.model;
public class User {
    private String id;
    private Role role;

         User(String id, Role role){
            this.id = id;
            this.role = role;

    }

    public String getId(){
        return id;
    }
    public Role getRole(){
        return role;
    }


}
//represents one person per system 
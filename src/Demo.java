import java.util.Optional;

public class Demo {
    public static void main(String[] args) {
        AccessPolicy policy = new DefaultAccessPolicy();
        CapabilityFactory factory = new PolicyBasedCapabilityFactory(policy);

        User student = new UserBuilder().setID("s1").setRole(Role.STUDENT).build();
        Resource<?> lecture = new LectureMaterial(); // Sample lecture resource used in the demo.

        Optional<Capability<? extends ReadAccess, Resource<?>>> cap = 
            factory.issueReadCapability(student, lecture);

        if (cap.isPresent()) {
            System.out.println("Access ALLOWED for " + student.getId());
        } else {
            System.out.println("Access DENIED");
        }
    }
}
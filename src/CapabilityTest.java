import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

public class CapabilityTest {

    @Test
    public void testStudentReadInternal() {
        // Configure the access policy and capability factory.
        AccessPolicy policy = new DefaultAccessPolicy();
        CapabilityFactory factory = new PolicyBasedCapabilityFactory(policy);

        // Create a student and the lecture resource being requested.
        User student = new UserBuilder().setID("s1").setRole(Role.STUDENT).build();
        Resource<AccessScope> lecture = new LectureMaterial();

        // Ask the factory for a read capability for the student.
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap = factory.issueReadCapability(student,
                lecture);

        // Confirm that access is granted and the capability belongs to the student.
        assertTrue(cap.isPresent(), "Student should be granted read access to Internal material");
        assertEquals("s1", cap.get().getUser().getId());
        System.out.println("Running test: Student Reading Internal Material...");

        cap = factory.issueReadCapability(student, lecture);

        assertTrue(cap.isPresent());
        System.out.println("Test Passed: Capability successfully issued!");
    }

    @Test
    public void testGuestDeniedInternal() {
        AccessPolicy policy = new DefaultAccessPolicy();
        CapabilityFactory factory = new PolicyBasedCapabilityFactory(policy);

        // Create a guest user attempting to read the lecture resource.
        User guest = new UserBuilder().setID("g1").setRole(Role.GUEST).build();
        Resource<AccessScope> lecture = new LectureMaterial();

        // Request a read capability and expect the policy to reject it.
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap = factory.issueReadCapability(guest,
                lecture);

        // Verify that no capability is returned for the guest.
        assertFalse(cap.isPresent(), "Guest must be denied access to Internal materials");
    }

}
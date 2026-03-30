import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

public class CapabilityTest {

    @Test
    public void testStudentReadInternal() {
        // 1. Setup the integrated system
        AccessPolicy policy = new DefaultAccessPolicy();
        CapabilityFactory factory = new PolicyBasedCapabilityFactory(policy);

        // 2. Create Student accessing Internal material
        User student = new UserBuilder().setID("s1").setRole(Role.STUDENT).build();
        Resource<AccessScope> lecture = new LectureMaterial();

        // 3. Request capability (Expect ALLOW)
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap = factory.issueReadCapability(student,
                lecture);

        // 4. Assertions
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

        // 2. Create Guest accessing Internal material
        User guest = new UserBuilder().setID("g1").setRole(Role.GUEST).build();
        Resource<AccessScope> lecture = new LectureMaterial();

        // 3. Request capability (Expect REFUSE)
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap = factory.issueReadCapability(guest,
                lecture);

        // 4. Assertions
        assertFalse(cap.isPresent(), "Guest must be denied access to Internal materials");
    }

}
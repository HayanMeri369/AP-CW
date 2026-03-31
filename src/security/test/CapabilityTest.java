package security.test;

import org.junit.jupiter.api.Test;
import security.capability.*;
import security.model.*;
import security.policy.DefaultAccessPolicy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the capability layer: ReadCapability, WriteCapability,
 * ReadWriteCapability, and PolicyBasedCapabilityFactory.
 */
class CapabilityTest {

    private final DefaultAccessPolicy  policy  = new DefaultAccessPolicy();
    private final CapabilityFactory    factory = new PolicyBasedCapabilityFactory(policy);

    private final User student = new UserBuilder().setId("s1").setRole(Role.STUDENT).build();
    private final User guest   = new UserBuilder().setId("g1").setRole(Role.GUEST).build();
    private final User staff   = new UserBuilder().setId("st1").setRole(Role.STAFF).build();
    private final User admin   = new UserBuilder().setId("a1").setRole(Role.ADMIN).build();

    private final Resource<AccessScope> printer  = new Printer();
    private final Resource<AccessScope> lecture  = new LectureMaterial();
    private final Resource<AccessScope> examPaper = new ExamPaper();

    // -----------------------------------------------------------------------
    // Read capability — granted cases
    // -----------------------------------------------------------------------

    @Test
    void testStudentGrantedReadOnInternalResource() {
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap =
                factory.issueReadCapability(student, lecture);
        assertTrue(cap.isPresent(), "Student should be granted read access to INTERNAL material");
        assertEquals("s1", cap.get().getUser().getId());
        assertNotNull(cap.get().getPermission());
        assertNotNull(cap.get().getIssuedAt());
    }

    @Test
    void testGuestGrantedReadOnPublicResource() {
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap =
                factory.issueReadCapability(guest, printer);
        assertTrue(cap.isPresent(), "Guest should be granted read access to PUBLIC resource");
    }

    @Test
    void testAdminGrantedReadOnConfidentialResource() {
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap =
                factory.issueReadCapability(admin, examPaper);
        assertTrue(cap.isPresent(), "Admin should be granted read access to CONFIDENTIAL resource");
    }

    // -----------------------------------------------------------------------
    // Read capability — denied cases
    // -----------------------------------------------------------------------

    @Test
    void testGuestDeniedReadOnInternalResource() {
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap =
                factory.issueReadCapability(guest, lecture);
        assertFalse(cap.isPresent(), "Guest must be denied read access to INTERNAL material");
    }

    @Test
    void testStudentDeniedReadOnConfidentialResource() {
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap =
                factory.issueReadCapability(student, examPaper);
        assertFalse(cap.isPresent(), "Student must be denied read access to CONFIDENTIAL resource");
    }

    // -----------------------------------------------------------------------
    // Write capability — granted cases
    // -----------------------------------------------------------------------

    @Test
    void testStaffGrantedWriteOnInternalResource() {
        Optional<Capability<? extends WriteAccess, Resource<AccessScope>>> cap =
                factory.issueWriteCapability(staff, lecture);
        assertTrue(cap.isPresent(), "Staff should be granted write access to INTERNAL resource");
    }

    @Test
    void testAdminGrantedWriteOnConfidentialResource() {
        Optional<Capability<? extends WriteAccess, Resource<AccessScope>>> cap =
                factory.issueWriteCapability(admin, examPaper);
        assertTrue(cap.isPresent(), "Admin should be granted write access to CONFIDENTIAL resource");
    }

    // -----------------------------------------------------------------------
    // Write capability — denied cases
    // -----------------------------------------------------------------------

    @Test
    void testStudentDeniedWrite() {
        Optional<Capability<? extends WriteAccess, Resource<AccessScope>>> cap =
                factory.issueWriteCapability(student, lecture);
        assertFalse(cap.isPresent(), "Student must not be granted write access");
    }

    @Test
    void testGuestDeniedWrite() {
        Optional<Capability<? extends WriteAccess, Resource<AccessScope>>> cap =
                factory.issueWriteCapability(guest, printer);
        assertFalse(cap.isPresent(), "Guest must not be granted write access");
    }

    // -----------------------------------------------------------------------
    // ReadWrite capability
    // -----------------------------------------------------------------------

    @Test
    void testAdminGrantedReadWriteCapability() {
        Optional<Capability<ReadWritePermission, Resource<AccessScope>>> cap =
                factory.issueReadWriteCapability(admin, examPaper);
        assertTrue(cap.isPresent(), "Admin should receive a ReadWrite capability");
        // ReadWritePermission implements both ReadAccess and WriteAccess
        assertTrue(cap.get().getPermission() instanceof ReadAccess);
        assertTrue(cap.get().getPermission() instanceof WriteAccess);
    }

    @Test
    void testStudentDeniedReadWriteCapability() {
        Optional<Capability<ReadWritePermission, Resource<AccessScope>>> cap =
                factory.issueReadWriteCapability(student, lecture);
        assertFalse(cap.isPresent(), "Student must not receive a ReadWrite capability");
    }

    // -----------------------------------------------------------------------
    // Capability fields
    // -----------------------------------------------------------------------

    @Test
    void testCapabilityStoresCorrectResource() {
        Optional<Capability<? extends ReadAccess, Resource<AccessScope>>> cap =
                factory.issueReadCapability(student, lecture);
        assertTrue(cap.isPresent());
        assertEquals("Lecture Material", cap.get().getResource().getName());
    }
}

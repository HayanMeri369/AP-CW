package security.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.log.AccessResult;
import security.log.AuditLog;
import security.model.*;
import security.policy.AccessController;
import security.policy.DefaultAccessPolicy;

import static org.junit.jupiter.api.Assertions.*;

/*
 Tests for the access policy layer DefaultAccessPolicy and AccessController

 */
class PolicyTest {

    private DefaultAccessPolicy policy;
    private AccessController    controller;

    private final User guest   = new UserBuilder().setId("g1").setRole(Role.GUEST).build();
    private final User student = new UserBuilder().setId("s1").setRole(Role.STUDENT).build();
    private final User staff   = new UserBuilder().setId("st1").setRole(Role.STAFF).build();
    private final User admin   = new UserBuilder().setId("a1").setRole(Role.ADMIN).build();

    private final Resource<?> printer   = new Printer();
    private final Resource<?> lecture   = new LectureMaterial();
    private final Resource<?> examPaper = new ExamPaper();

    @BeforeEach
    void setUp() {
        policy     = new DefaultAccessPolicy();
        AuditLog.getInstance().clearForTesting();
        controller = new AccessController(policy, AuditLog.getInstance());
    }

    // GUEST rules


    @Test
    void testGuestAllowedPublicRead() {
        assertTrue(policy.isAllowed(guest, printer, Operation.READ));
    }

    @Test
    void testGuestDeniedPublicWrite() {
        assertFalse(policy.isAllowed(guest, printer, Operation.WRITE));
    }

    @Test
    void testGuestDeniedInternalRead() {
        assertFalse(policy.isAllowed(guest, lecture, Operation.READ));
    }

    @Test
    void testGuestDeniedConfidentialRead() {
        assertFalse(policy.isAllowed(guest, examPaper, Operation.READ));
    }


    // STUDENT rules


    @Test
    void testStudentAllowedPublicRead() {
        assertTrue(policy.isAllowed(student, printer, Operation.READ));
    }

    @Test
    void testStudentAllowedInternalRead() {
        assertTrue(policy.isAllowed(student, lecture, Operation.READ));
    }

    @Test
    void testStudentDeniedConfidentialRead() {
        assertFalse(policy.isAllowed(student, examPaper, Operation.READ));
    }

    @Test
    void testStudentDeniedInternalWrite() {
        assertFalse(policy.isAllowed(student, lecture, Operation.WRITE));
    }

    @Test
    void testStudentDeniedConfidentialWrite() {
        assertFalse(policy.isAllowed(student, examPaper, Operation.WRITE));
    }


    // STAFF rules


    @Test
    void testStaffAllowedPublicReadWrite() {
        assertTrue(policy.isAllowed(staff, printer, Operation.READ));
        assertTrue(policy.isAllowed(staff, printer, Operation.WRITE));
    }

    @Test
    void testStaffAllowedInternalReadWrite() {
        assertTrue(policy.isAllowed(staff, lecture, Operation.READ));
        assertTrue(policy.isAllowed(staff, lecture, Operation.WRITE));
    }

    @Test
    void testStaffDeniedConfidentialAccess() {
        assertFalse(policy.isAllowed(staff, examPaper, Operation.READ));
        assertFalse(policy.isAllowed(staff, examPaper, Operation.WRITE));
    }

    // ADMIN rules


    @Test
    void testAdminAllowedEverything() {
        assertTrue(policy.isAllowed(admin, printer,   Operation.READ));
        assertTrue(policy.isAllowed(admin, printer,   Operation.WRITE));
        assertTrue(policy.isAllowed(admin, lecture,   Operation.READ));
        assertTrue(policy.isAllowed(admin, lecture,   Operation.WRITE));
        assertTrue(policy.isAllowed(admin, examPaper, Operation.READ));
        assertTrue(policy.isAllowed(admin, examPaper, Operation.WRITE));
    }

    // AccessController result and logging


    @Test
    void testControllerReturnsAllowForPermittedRequest() {
        AccessResult result = controller.request(student, lecture, Operation.READ);
        assertEquals(AccessResult.ALLOW, result);
    }

    @Test
    void testControllerReturnsRefuseForDeniedRequest() {
        AccessResult result = controller.request(student, examPaper, Operation.READ);
        assertEquals(AccessResult.REFUSE, result);
    }

    @Test
    void testControllerLogsEachRequest() {
        controller.request(guest,   printer,   Operation.READ);
        controller.request(student, examPaper, Operation.WRITE);
        assertEquals(2, AuditLog.getInstance().size());
    }

    @Test
    void testControllerThrowsIfUserIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                controller.request(null, printer, Operation.READ)
        );
    }
}

package security.test;

import org.junit.jupiter.api.Test;
import security.model.*;

import static org.junit.jupiter.api.Assertions.*;


class ModelTest {

    // Role enum


    @Test
    void testRoleValuesExist() {
        assertNotNull(Role.GUEST);
        assertNotNull(Role.STUDENT);
        assertNotNull(Role.STAFF);
        assertNotNull(Role.ADMIN);
        assertEquals(4, Role.values().length);
    }

    // AccessScope enum


    @Test
    void testAccessScopeValuesExist() {
        assertNotNull(AccessScope.PUBLIC);
        assertNotNull(AccessScope.INTERNAL);
        assertNotNull(AccessScope.CONFIDENTIAL);
        assertEquals(3, AccessScope.values().length);
    }

        // User and UserBuilder


    @Test
    void testUserBuilderCreatesUserWithCorrectFields() {
        User user = new UserBuilder().setId("u1").setRole(Role.STUDENT).build();
        assertEquals("u1",         user.getId());
        assertEquals(Role.STUDENT, user.getRole());
    }

    @Test
    void testUserBuilderThrowsIfIdIsNull() {
        assertThrows(IllegalStateException.class, () ->
                new UserBuilder().setRole(Role.GUEST).build()
        );
    }

    @Test
    void testUserBuilderThrowsIfIdIsBlank() {
        assertThrows(IllegalStateException.class, () ->
                new UserBuilder().setId("   ").setRole(Role.GUEST).build()
        );
    }

    @Test
    void testUserBuilderThrowsIfRoleIsNull() {
        assertThrows(IllegalStateException.class, () ->
                new UserBuilder().setId("u1").build()
        );
    }

    @Test
    void testUserBuilderIsChainable() {
        // Verifies that setId and setRole both return the builder (fluent API)
        UserBuilder builder = new UserBuilder();
        assertSame(builder, builder.setId("u2"));
        assertSame(builder, builder.setRole(Role.ADMIN));
    }


    // Resource subclasses correct names and scopes


    @Test
    void testPrinterHasPublicScope() {
        Resource<?> printer = new Printer();
        assertEquals("Printer",        printer.getName());
        assertEquals(AccessScope.PUBLIC, printer.getScope());
    }

    @Test
    void testLectureMaterialHasInternalScope() {
        Resource<?> lecture = new LectureMaterial();
        assertEquals("Lecture Material",   lecture.getName());
        assertEquals(AccessScope.INTERNAL, lecture.getScope());
    }

    @Test
    void testExamPaperHasConfidentialScope() {
        Resource<?> exam = new ExamPaper();
        assertEquals("Exam Paper",              exam.getName());
        assertEquals(AccessScope.CONFIDENTIAL,  exam.getScope());
    }

    @Test
    void testResourceThrowsIfNameIsBlank() {
        // Verifies the Resource constructor validates its inputs
        assertThrows(IllegalArgumentException.class, () ->
                new Resource<AccessScope>("", AccessScope.PUBLIC) {}
        );
    }

    @Test
    void testResourceThrowsIfScopeIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Resource<AccessScope>("Test", null) {}
        );
    }
}

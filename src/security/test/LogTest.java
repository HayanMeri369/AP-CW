package security.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.log.AccessResult;
import security.log.AuditLog;
import security.log.LogEntry;
import security.model.Operation;
import security.model.Role;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class LogTest {

    private static final LocalDateTime FIXED_TIME =
            LocalDateTime.of(2025, 1, 20, 16, 0);

    @BeforeEach
    void resetLog() {
        AuditLog.getInstance().clearForTesting();
    }


    // AccessResult


    @Test
    void testAccessResultValuesExist() {
        assertNotNull(AccessResult.ALLOW);
        assertNotNull(AccessResult.REFUSE);
        assertEquals(2, AccessResult.values().length);
    }


    // LogEntry  field storage and formatting

    @Test
    void testLogEntryStoresFieldsCorrectly() {
        LogEntry entry = new LogEntry(
                FIXED_TIME, "user2", Role.ADMIN,
                "Exam Paper", Operation.WRITE, AccessResult.ALLOW
        );
        assertEquals("user2",            entry.getUserId());
        assertEquals(Role.ADMIN,          entry.getRole());
        assertEquals("Exam Paper",        entry.getResourceName());
        assertEquals(Operation.WRITE,     entry.getOperation());
        assertEquals(AccessResult.ALLOW,  entry.getResult());
        assertEquals(FIXED_TIME,          entry.getTimestamp());
    }

    @Test
    void testLogEntryToStringMatchesSpec() {
        LogEntry entry = new LogEntry(
                FIXED_TIME, "user2", Role.ADMIN,
                "Exam Paper", Operation.WRITE, AccessResult.ALLOW
        );
        assertEquals("20-01-2025 16:00, user2, ADMIN, Exam Paper, WRITE, ALLOW",
                entry.toString());
    }

    @Test
    void testLogEntryToStringWithRefuse() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 21, 18, 0);
        LogEntry entry = new LogEntry(
                time, "user2", Role.STUDENT,
                "Exam Paper", Operation.WRITE, AccessResult.REFUSE
        );
        assertEquals("21-01-2025 18:00, user2, STUDENT, Exam Paper, WRITE, REFUSE",
                entry.toString());
    }

    @Test
    void testLogEntryUsesCurrentTimestampWhenNotProvided() {
        LogEntry entry = new LogEntry("user1", Role.STUDENT,
                "Lecture Material", Operation.READ, AccessResult.ALLOW);
        assertNotNull(entry.getTimestamp());
    }


    // LogEntry  validation


    @Test
    void testLogEntryThrowsIfUserIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new LogEntry(FIXED_TIME, "", Role.GUEST,
                        "Printer", Operation.READ, AccessResult.ALLOW)
        );
    }

    @Test
    void testLogEntryThrowsIfRoleIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new LogEntry(FIXED_TIME, "user1", null,
                        "Printer", Operation.READ, AccessResult.ALLOW)
        );
    }

    @Test
    void testLogEntryThrowsIfResultIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new LogEntry(FIXED_TIME, "user1", Role.GUEST,
                        "Printer", Operation.READ, null)
        );
    }


    // AuditLog


    @Test
    void testAuditLogSingletonReturnsSameInstance() {
        AuditLog instance1 = AuditLog.getInstance();
        AuditLog instance2 = AuditLog.getInstance();
        assertSame(instance1, instance2,
                "AuditLog.getInstance() must always return the same object");
    }



    @Test
    void testAuditLogStoresEntry() {
        AuditLog log = AuditLog.getInstance();
        LogEntry entry = new LogEntry(
                FIXED_TIME, "user1", Role.STUDENT,
                "Lecture Material", Operation.READ, AccessResult.ALLOW
        );
        log.log(entry);
        assertEquals(1, log.size());
        assertEquals(entry, log.getAll().get(0));
    }

    @Test
    void testAuditLogStoresMultipleEntries() {
        AuditLog log = AuditLog.getInstance();
        log.log(new LogEntry(FIXED_TIME, "user1", Role.STUDENT,
                "Printer", Operation.READ, AccessResult.ALLOW));
        log.log(new LogEntry(FIXED_TIME, "user2", Role.ADMIN,
                "Exam Paper", Operation.WRITE, AccessResult.ALLOW));
        log.log(new LogEntry(FIXED_TIME, "user3", Role.GUEST,
                "Exam Paper", Operation.READ, AccessResult.REFUSE));
        assertEquals(3, log.size());
    }

    @Test
    void testAuditLogThrowsIfEntryIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                AuditLog.getInstance().log(null)
        );
    }

    @Test
    void testGetAllReturnsUnmodifiableList() {
        AuditLog log = AuditLog.getInstance();
        log.log(new LogEntry(FIXED_TIME, "user1", Role.GUEST,
                "Printer", Operation.READ, AccessResult.ALLOW));
        List<LogEntry> all = log.getAll();
        assertThrows(UnsupportedOperationException.class, () -> all.add(null));
    }


    @Test
    void testFilterByUser() {
        AuditLog log = AuditLog.getInstance();
        log.log(new LogEntry(FIXED_TIME, "alice", Role.STAFF,
                "Printer", Operation.READ, AccessResult.ALLOW));
        log.log(new LogEntry(FIXED_TIME, "bob", Role.STUDENT,
                "Lecture Material", Operation.READ, AccessResult.ALLOW));
        log.log(new LogEntry(FIXED_TIME, "alice", Role.STAFF,
                "Exam Paper", Operation.WRITE, AccessResult.ALLOW));

        List<LogEntry> aliceEntries = log.filterByUser("alice");
        assertEquals(2, aliceEntries.size());
        assertTrue(aliceEntries.stream().allMatch(e -> e.getUserId().equals("alice")));
    }

    @Test
    void testFilterByResultAllow() {
        AuditLog log = AuditLog.getInstance();
        log.log(new LogEntry(FIXED_TIME, "user1", Role.STUDENT,
                "Printer", Operation.READ, AccessResult.ALLOW));
        log.log(new LogEntry(FIXED_TIME, "user1", Role.STUDENT,
                "Exam Paper", Operation.READ, AccessResult.REFUSE));
        log.log(new LogEntry(FIXED_TIME, "user2", Role.ADMIN,
                "Exam Paper", Operation.WRITE, AccessResult.ALLOW));

        List<LogEntry> allowed = log.filterByResult(AccessResult.ALLOW);
        assertEquals(2, allowed.size());
        assertTrue(allowed.stream().allMatch(e -> e.getResult() == AccessResult.ALLOW));
    }

    @Test
    void testFilterByResultRefuse() {
        AuditLog log = AuditLog.getInstance();
        log.log(new LogEntry(FIXED_TIME, "user1", Role.GUEST,
                "Exam Paper", Operation.READ, AccessResult.REFUSE));
        log.log(new LogEntry(FIXED_TIME, "user2", Role.STUDENT,
                "Exam Paper", Operation.WRITE, AccessResult.REFUSE));
        log.log(new LogEntry(FIXED_TIME, "user3", Role.ADMIN,
                "Exam Paper", Operation.READ, AccessResult.ALLOW));

        List<LogEntry> refused = log.filterByResult(AccessResult.REFUSE);
        assertEquals(2, refused.size());
    }

    @Test
    void testGenericFilterByOperation() {
        AuditLog log = AuditLog.getInstance();
        log.log(new LogEntry(FIXED_TIME, "user1", Role.STAFF,
                "Printer", Operation.READ, AccessResult.ALLOW));
        log.log(new LogEntry(FIXED_TIME, "user1", Role.STAFF,
                "Lecture Material", Operation.WRITE, AccessResult.ALLOW));
        log.log(new LogEntry(FIXED_TIME, "user2", Role.ADMIN,
                "Exam Paper", Operation.WRITE, AccessResult.ALLOW));

        List<LogEntry> writes = log.filter(e -> e.getOperation() == Operation.WRITE);
        assertEquals(2, writes.size());
    }

    @Test
    void testFilterReturnsEmptyListWhenNoMatch() {
        AuditLog log = AuditLog.getInstance();
        log.log(new LogEntry(FIXED_TIME, "user1", Role.STUDENT,
                "Printer", Operation.READ, AccessResult.ALLOW));

        List<LogEntry> result = log.filterByUser("nobody");
        assertTrue(result.isEmpty());
    }
}

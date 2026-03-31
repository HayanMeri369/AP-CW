package security.capability;

import security.model.Resource;
import security.model.User;

import java.time.LocalDateTime;

/**
 * Represents a time-stamped permission granted to a user for a specific resource.
 *
 * JAVA GENERICS: Capability<P extends Permission, R extends Resource<?>>
 * uses two bounded type parameters:
 *
 *   P — the permission type (ReadAccess, WriteAccess, or both).
 *       The bound ensures only valid Permission subtypes are used,
 *       and the compiler rejects e.g. Capability<String, ...>.
 *
 *   R — the exact resource type this capability covers.
 *       This means a Capability<ReadAccess, ExamPaper> is distinct
 *       from a Capability<ReadAccess, Printer> at compile time,
 *       so you cannot accidentally use an exam-paper capability on a printer.
 */
public interface Capability<P extends Permission, R extends Resource<?>> {
    User            getUser();
    R               getResource();
    P               getPermission();
    LocalDateTime   getIssuedAt();
}

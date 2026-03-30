package security.log;

import security.model.Operation;
import security.model.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final LocalDateTime timestamp;
    private final String userId;
    private final Role role;
    private final String resourceName;
    private final Operation operation;
    private final AccessResult result;


    public LogEntry(String userId,
                    Role role,
                    String resourceName,
                    Operation operation,
                    AccessResult result) {
        this(LocalDateTime.now(), userId, role, resourceName, operation, result);
    }

    public LogEntry(LocalDateTime timestamp,
                    String userId,
                    Role role,
                    String resourceName,
                    Operation operation,
                    AccessResult result) {
        if (timestamp == null)    throw new IllegalArgumentException("Timestamp must not be null.");
        if (userId == null || userId.isBlank())
            throw new IllegalArgumentException("User ID must not be null or blank.");
        if (role == null)         throw new IllegalArgumentException("Role must not be null.");
        if (resourceName == null || resourceName.isBlank())
            throw new IllegalArgumentException("Resource name must not be null or blank.");
        if (operation == null)    throw new IllegalArgumentException("Operation must not be null.");
        if (result == null)       throw new IllegalArgumentException("Result must not be null.");

        this.timestamp    = timestamp;
        this.userId       = userId;
        this.role         = role;
        this.resourceName = resourceName;
        this.operation    = operation;
        this.result       = result;
    }

    // Getters — used by AuditLog filters and tests

    public LocalDateTime getTimestamp()  { return timestamp; }
    public String        getUserId()     { return userId; }
    public Role          getRole()       { return role; }
    public String        getResourceName() { return resourceName; }
    public Operation     getOperation()  { return operation; }
    public AccessResult  getResult()     { return result; }

    /**
     * Returns the log entry formatted
     *
     */
    @Override
    public String toString() {
        return String.join(", ",
                timestamp.format(FORMATTER),
                userId,
                role.toString(),
                resourceName,
                operation.toString(),
                result.toString()
        );
    }
}
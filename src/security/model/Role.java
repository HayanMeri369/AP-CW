package security.model;

/**
 * User roles recognised by the access-control policy.
 * Each role has a different level of access to resources.
 */
public enum Role {
    GUEST,
    STUDENT,
    STAFF,
    ADMIN
}

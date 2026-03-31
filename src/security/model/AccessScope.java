package security.model;

/**
 * Visibility levels used to classify protected resources.
 *
 *  PUBLIC       – anyone can read
 *  INTERNAL     – students and staff can read
 *  CONFIDENTIAL – restricted access (ADMIN only, or STAFF write)
 */
public enum AccessScope {
    PUBLIC,
    INTERNAL,
    CONFIDENTIAL
}

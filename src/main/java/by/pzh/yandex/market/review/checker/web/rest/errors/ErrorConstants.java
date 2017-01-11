package by.pzh.yandex.market.review.checker.web.rest.errors;

/**
 * A set of constants for ERROR handling.
 */
public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "Concurrency failure error";
    public static final String ERR_ACCESS_DENIED = "Access for operation is denied";
    public static final String ERR_VALIDATION = "Validation error(s) occurred";
    public static final String ERR_METHOD_NOT_SUPPORTED = "Method call error - method not supported";
    public static final String ERR_INTERNAL_SERVER_ERROR = "Operation wasn't successful, an error occurred.";
    public static final String ERR_ENTITY_NOT_FOUND = "Resource with specified params does not exist.";
    public static final String ERR_ENTITY_NOT_UPDATED = "No one resource has been updated.";
    public static final String ERR_INVALID_FILE = "File or data inside file is not valid.";
 
    /**
     * Default private c-r.
     */
    private ErrorConstants() {


    }

}

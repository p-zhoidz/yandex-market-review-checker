package by.pzh.yandex.market.review.checker.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    /**
     * Utility class default constructor.
     */
    private HeaderUtil() {
    }

    /**
     * Create alert headers.
     *
     * @param message message.
     * @param param   params.
     * @return {@link HttpHeaders}.
     */
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-YMRCApp-alert", message);
        headers.add("X-YMRCApp-params", param);
        return headers;
    }

    /**
     * Entity not found headers.
     *
     * @param entityClass class of the entity.
     * @param message     message.
     * @return {@link HttpHeaders}.
     */
    public static HttpHeaders createEntityNotFoundException(String entityClass, String message) {
        return createAlert(message, entityClass);
    }

    /**
     * Constraint violation headers.
     *
     * @param constraintName name of the constraint.
     * @param currentValue   current value.
     * @return {@link HttpHeaders}.
     */
    public static HttpHeaders createConstraintViolationException(String constraintName, String currentValue) {
        String builder = "A record with " + constraintName + " \"" + currentValue + "\""
                + " already exists. Please change it and try again.";
        return createAlert(builder, currentValue);

    }
}
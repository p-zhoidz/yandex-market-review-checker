package by.pzh.yandex.market.review.checker.commons.exceptions;

/**
 * @author p.zhoidz.
 */
public class EntityExistsException extends RuntimeException {

    /**
     * Default c-r.
     *
     * @param entityName  entity name
     * @param entityValue entity value
     */
    public EntityExistsException(String entityName, String entityValue) {
        super(entityName + " " + entityValue + " " + "already exists.");
    }
}

package by.pzh.yandex.market.review.checker.commons.exceptions;

/**
 * @author p.zhoidz.
 */
public class EntityNotFoundException extends RuntimeException {
    private String clazzName;
    private String message;

    /**
     * Public default c-r with parameters.
     *
     * @param clazz   class(type)
     * @param message message
     */
    public EntityNotFoundException(Class clazz, String message) {
        this.clazzName = clazz.getName();
        this.message = message;
    }

    /**
     * @return Returns the value of clazz.
     */
    public String getClassName() {
        return clazzName;
    }

    @Override
    public String getMessage() {
        return "Entity " + clazzName + " " + message + " value does not exist.";
    }

}

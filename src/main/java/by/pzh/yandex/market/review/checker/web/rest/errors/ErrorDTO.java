package by.pzh.yandex.market.review.checker.web.rest.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transferring error message with a list of field errors.
 */
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;

    private List<FieldErrorDTO> fieldErrors;

    /**
     * Parametrized constructor.
     *
     * @param message Message.
     */
    public ErrorDTO(String message) {
        this(message, null);
    }

    /**
     * Parametrized constructor.
     *
     * @param message     Message.
     * @param description Description.
     */
    public ErrorDTO(String message, String description) {
        this.message = message;
        this.description = description;
    }


    /**
     * Parametrized constructor.
     *
     * @param message     Message.
     * @param description Description.
     * @param fieldErrors Field errors.
     */
    public ErrorDTO(String message, String description, List<FieldErrorDTO> fieldErrors) {
        this(message, description);
        this.fieldErrors = fieldErrors;
    }

    /**
     * Add field error.
     *
     * @param objectName Object name.
     * @param field      Field name.
     * @param message    Message.
     */
    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, message));
    }

    /**
     * @return Returns the value of fieldErrors.
     */
    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * @return Returns the value of description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns the value of message.
     */
    public String getMessage() {
        return message;
    }
}

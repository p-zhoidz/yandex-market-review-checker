package by.pzh.yandex.market.review.checker.web.rest.errors;

import java.io.Serializable;

/**
 * Field error DTO class.
 */
public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;

    /**
     * Parametrized constructor.
     *
     * @param dto     dto.
     * @param field   field name.
     * @param message maeesage.
     */
    public FieldErrorDTO(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    /**
     * @return Returns the value of objectName.
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @return Returns the value of field.
     */
    public String getField() {
        return field;
    }

    /**
     * @return Returns the value of message.
     */
    public String getMessage() {
        return message;
    }
}

package by.pzh.yandex.market.review.checker.service.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author p.zhoidz.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExecutionStatus {
    NOT_FOUND(4004, "Service was unable to find any data matching passed parameters."),
    NOT_UPDATED(4005, "Service was able to process request in accordance to the passed params, "
            + "but no changes where detected."),
    SUCCESS(2000, "Service successfully executed request."),
    FOUND(2002, "Resource found."),
    UPDATED(2003, "Resource has been updated."),
    POTENTIAL_CONFLICT(4008, "Resource with specified parameters already exists.");

    private final Integer code;
    private final String message;

    /**
     * Parametrized constructor.
     *
     * @param code    status code.
     * @param message message.
     */
    ExecutionStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return Returns the value of code.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @return Returns the value of message.
     */
    public String getMessage() {
        return message;
    }
}

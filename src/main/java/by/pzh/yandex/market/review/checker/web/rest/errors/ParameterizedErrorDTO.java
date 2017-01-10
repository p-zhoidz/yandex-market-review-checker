package by.pzh.yandex.market.review.checker.web.rest.errors;

import java.io.Serializable;
import java.util.Arrays;

/**
 * DTO for sending a parameterized error message.
 */
public class ParameterizedErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String message;
    private final String[] params;

    /**
     * Parametrized constructor.
     *
     * @param message message
     * @param params  params.
     */
    public ParameterizedErrorDTO(String message, String... params) {
        this.message = message;
        this.params = params;
    }

    /**
     * @return Returns the value of message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the value of params.
     */
    public String[] getParams() {
        return Arrays.copyOf(params, params.length);
    }
}

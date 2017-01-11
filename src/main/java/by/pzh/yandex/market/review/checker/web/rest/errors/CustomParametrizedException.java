package by.pzh.yandex.market.review.checker.web.rest.errors;

/**
 * Custom, parameterized exception, which can be translated on the client side.
 * For example:
 * <p>
 * <pre>
 * throw new CustomParametrizedException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
 * </pre>
 * <p>
 * Can be translated with:
 * <p>
 * <pre>
 * "error.myCustomError" :  "The server says {{params[0]}} to {{params[1]}}"
 * </pre>
 */
public class CustomParametrizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String[] params;

    /**
     * Default c-r with parameters.
     *
     * @param message message
     * @param params  params
     */
    public CustomParametrizedException(String message, String... params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    /**
     * Returns {@link ParameterizedErrorDTO}.
     *
     * @return instance of {@link ParameterizedErrorDTO}
     */
    public ParameterizedErrorDTO getErrorDTO() {
        return new ParameterizedErrorDTO(message, params);
    }

}

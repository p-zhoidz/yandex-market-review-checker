package by.pzh.yandex.market.review.checker.web.rest.endpoints.resp;

import by.pzh.yandex.market.review.checker.service.constants.ExecutionStatus;

/**
 * Wrapper for the Http response.
 *
 * @author p.zhoidz.
 */
public class ResponseWrapper {

    private Object response;
    private ExecutionStatus executionStatus;

    /**
     * Parametrized constructor.
     *
     * @param response        response body.
     * @param executionStatus internal representation of the status.
     */
    public ResponseWrapper(Object response, ExecutionStatus executionStatus) {
        this.response = response;
        this.executionStatus = executionStatus;
    }

    /**
     * @return Returns the value of response.
     */
    public Object getResponse() {
        return response;
    }

    /**
     * @return Returns the value of executionStatus.
     */
    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }
}

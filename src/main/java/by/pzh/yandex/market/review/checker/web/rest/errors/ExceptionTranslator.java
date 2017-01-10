package by.pzh.yandex.market.review.checker.web.rest.errors;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class ExceptionTranslator {

    /**
     * ConcurrencyFailureException handler.
     *
     * @param ex Exception.
     * @return User friendly {@link ErrorDTO}.
     */
    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorDTO processConcurencyError(ConcurrencyFailureException ex) {
        return new ErrorDTO(ErrorConstants.ERR_CONCURRENCY_FAILURE);
    }


    /**
     * ConcurrencyFailureException handler.
     *
     * @param ex Exception.
     * @return User friendly {@link ErrorDTO}.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ErrorDTO processEntityNotFoundExceptio(EntityNotFoundException ex) {
        return new ErrorDTO(ErrorConstants.ERR_ENTITY_NOT_FOUND);
    }


    /**
     * MethodArgumentNotValidException handler.
     *
     * @param ex Exception.
     * @return User friendly {@link ErrorDTO}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    /**
     * CustomParametrizedException handler.
     *
     * @param ex Exception.
     * @return User friendly {@link ErrorDTO}.
     */
    @ExceptionHandler(CustomParametrizedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ParameterizedErrorDTO processParameterizedValidationError(CustomParametrizedException ex) {
        return ex.getErrorDTO();
    }

    /**
     * AccessDeniedException handler.
     *
     * @param ex Exception.
     * @return User friendly {@link ErrorDTO}.
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO processAccessDeniedException(AccessDeniedException ex) {
        return new ErrorDTO(ErrorConstants.ERR_ACCESS_DENIED, ex.getMessage());
    }

    /**
     * HttpRequestMethodNotSupportedException handler.
     *
     * @param ex Exception.
     * @return User friendly {@link ErrorDTO}.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDTO processMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ErrorDTO(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, ex.getMessage());
    }

    /**
     * Exception handler.
     *
     * @param ex Exception.
     * @return User friendly {@link ResponseEntity<ErrorDTO>}.
     * @throws Exception standard exception cases.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> processRuntimeException(Exception ex) throws Exception {
        BodyBuilder builder;
        ErrorDTO errorDTO;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorDTO = new ErrorDTO("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorDTO = new ErrorDTO(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return builder.body(errorDTO);
    }

    /**
     * Convert field errors to the User friendly format.
     *
     * @param fieldErrors list of the field errors.
     * @return {@link ErrorDTO}
     */
    private ErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ErrorDTO dto = new ErrorDTO(ErrorConstants.ERR_VALIDATION);

        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }

        return dto;
    }
}

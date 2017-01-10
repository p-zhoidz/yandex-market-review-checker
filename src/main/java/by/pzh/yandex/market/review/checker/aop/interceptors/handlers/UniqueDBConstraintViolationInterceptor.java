package by.pzh.yandex.market.review.checker.aop.interceptors.handlers;

import by.pzh.yandex.market.review.checker.web.rest.util.HeaderUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Aspect to catch and handle unique DB constraints violations.
 * Methods which can potentially throw ConstraintViolationException should be
 * annotated with @ThrowsUniqueConstraintException.
 * In this case, aspect will catch exception and generate response (CONFLICT 409) with information with
 * filed is duplicated.
 *
 * @author p.zhoidz.
 */
@Aspect
public class UniqueDBConstraintViolationInterceptor {

    /**
     * point cut for interceptor.
     */
    @Pointcut(value = "@annotation(by.pzh.yandex.market.review.checker.aop."
            + "interceptors.anootations.ThrowsUniqueConstraintException))")
    public void constraintViolationPointcut() {
    }

    /**
     * Handle violation cases. Detect violation field and value and send response to the client.
     *
     * @param joinPoint aspect join point.
     * @return in case of normal flow, result of the annotated method,
     * otherwise response with constraint violation message.
     * @throws Throwable if caught exception is not related to the unique constraint violation, exception is rethrown.
     */
    @Around("constraintViolationPointcut()")
    public ResponseEntity<?> handleViolation(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            return (ResponseEntity<?>) joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            if (throwable.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) throwable.getCause();

                String constraintName = cause.getConstraintName();
                String message = cause.getSQLException().getMessage();
                String duplicateEntry = getDuplicateEntry(message);

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .headers(HeaderUtil.createConstraintViolationException(ConstraintVocabulary
                                .getValue(constraintName), duplicateEntry))
                        .build();
            }
            throw throwable;
        }
    }

    /**
     * Retrieve value of the duplicated entry.
     *
     * @param message source to parse.
     * @return retrieved value of the duplicated entry.
     */
    protected String getDuplicateEntry(String message) {
        return message.substring(message.indexOf("'") + 1,
                message.indexOf("'", message.indexOf("'") + 1));
    }


    /**
     * Since Constraint violation response contains DB related constraint name, matching is needed.
     * Class contains predefined key - value pairs.
     */
    public static class ConstraintVocabulary {
        public static final Map<String, String> VOCABULARY;

        static {
            HashMap<String, String> constraints = new HashMap<>();
            constraints.put("user_email_UNIQUE", "E-mail");
            constraints.put("company_name_UNIQUE", "Company name");
            constraints.put("email_company_UNIQUE", "Company Employee Email");

            VOCABULARY = Collections.unmodifiableMap(constraints);
        }

        /**
         * Retrieve value of the constrain name which will be send in the response.
         *
         * @param key to retrieve value for.
         * @return value for specified key.
         */
        public static String getValue(String key) {
            Optional<String> optional = Optional.ofNullable(VOCABULARY.get(key));
            return optional.orElseThrow(()
                    -> new NoSuchElementException("Value for the unique key " + key + " not found"));
        }

    }
}

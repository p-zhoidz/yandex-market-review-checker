package by.pzh.yandex.market.review.checker.aop.logging;

import by.pzh.yandex.market.review.checker.config.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private Environment env;

    /**
     * Logging aspect pointcut.
     */
    @Pointcut("within(by.pzh.yandex.market.review.checker.repository..*) "
            + "|| within(by.pzh.yandex.market.review.checker.service..*) "
            + "|| within(by.pzh.yandex.market.review.checker.web.rest..*)")
    public void loggingPointcut() {
    }

    /**
     * After throwing directives.
     *
     * @param joinPoint joint point.
     * @param e         exception.
     */
    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_LOCAL)) {
            log.error("Exception in {}.{}() with cause = {} and exception {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause(), e);
        } else {
            log.error("Exception in {}.{}() with cause = {} with stack trace= {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause(), e.getStackTrace());
        }
    }

    /**
     * Around pointcut.
     *
     * @param joinPoint joint pooint
     * @return result of the function.
     * @throws Throwable standard exception cases.
     */
    @Around("loggingPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
}

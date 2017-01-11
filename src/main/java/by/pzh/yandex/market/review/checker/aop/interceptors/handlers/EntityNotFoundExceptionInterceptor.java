package by.pzh.yandex.market.review.checker.aop.interceptors.handlers;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import by.pzh.yandex.market.review.checker.web.rest.util.HeaderUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * {@link EntityNotFoundException} exception interceptor.
 * Catches message and generates appropriate response to the client.
 *
 * @author p.zhoidz.
 */
@Aspect
public class EntityNotFoundExceptionInterceptor {

    /**
     * Point cut for {@link EntityNotFoundExceptionInterceptor} triggering.
     */
    @Pointcut(value = "@annotation(by.pzh.yandex.market.review.checker.aop."
            + "interceptors.anootations.ThrowsNotFoundException))")
    public void entityNotFoundExceptionPointcut() {
    }

    /**
     * Handle method for interceptor.
     *
     * @param proceedingJoinPoint proceeding join point
     * @return instance of {@link ResponseEntity}
     * @throws Throwable throwable
     */
    @Around("entityNotFoundExceptionPointcut()")
    public ResponseEntity intercept(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return (ResponseEntity) proceedingJoinPoint.proceed();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .headers(HeaderUtil.createEntityNotFoundException(ex.getClassName(), ex.getMessage()))
                    .build();
        }

    }
}

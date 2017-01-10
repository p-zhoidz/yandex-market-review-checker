package by.pzh.yandex.market.review.checker.config;

import by.pzh.yandex.market.review.checker.aop.interceptors.handlers.EntityNotFoundExceptionInterceptor;
import by.pzh.yandex.market.review.checker.aop.interceptors.handlers.UniqueDBConstraintViolationInterceptor;
import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration for AOP.
 *
 * @author p.zhoidz.
 */
@Configuration
@EnableAspectJAutoProxy
public class AOPInterceptorsConfiguration {

    /**
     * Interceptor for method that
     * can throw {@link EntityNotFoundException}.
     *
     * @return bean of {@link EntityNotFoundExceptionInterceptor}
     */
    @Bean
    public EntityNotFoundExceptionInterceptor notFoundInterceptor() {
        return new EntityNotFoundExceptionInterceptor();
    }

    /**
     * Interceptor for method that can cause {@link ConstraintViolationException}.
     *
     * @return bean of {@link UniqueDBConstraintViolationInterceptor}
     */
    @Bean
    public UniqueDBConstraintViolationInterceptor uniqueConstraintInterceptor() {
        return new UniqueDBConstraintViolationInterceptor();
    }
}
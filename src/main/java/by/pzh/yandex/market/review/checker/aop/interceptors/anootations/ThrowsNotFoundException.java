package by.pzh.yandex.market.review.checker.aop.interceptors.anootations;

import by.pzh.yandex.market.review.checker.commons.exceptions.EntityNotFoundException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods which can throw
 * {@link EntityNotFoundException} exception.
 * If method is annotated, and it throws exception, than aspect takes care of the situation.
 * Important:
 * Only method which return {@link org.springframework.http.ResponseEntity} can be annotated.
 *
 * @author p.zhoidz.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ThrowsNotFoundException {

}

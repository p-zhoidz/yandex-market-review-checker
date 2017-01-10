package by.pzh.yandex.market.review.checker.aop.interceptors.anootations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods which can throw DB Unique constraint violation.
 * If method is annotated, and it throws exception, than aspect takes care of the situation.
 * Important:
 * When used not on the single DB constraint etc. constraint on multiple fields, final message could be incorrect.
 *
 * @author p.zhoidz.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ThrowsUniqueConstraintException {
}

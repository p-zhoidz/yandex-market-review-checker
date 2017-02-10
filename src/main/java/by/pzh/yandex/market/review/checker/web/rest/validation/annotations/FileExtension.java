package by.pzh.yandex.market.review.checker.web.rest.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * File extension validation annotation.
 *
 * @author p.zhoidz.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileExtension {
    /**
     * Get array of possible file type extensions.
     *
     * @return Array of possible file type extensions.
     */
    String[] extensions();

}

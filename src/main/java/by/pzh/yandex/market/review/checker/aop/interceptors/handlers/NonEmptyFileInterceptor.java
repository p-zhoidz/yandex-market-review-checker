package by.pzh.yandex.market.review.checker.aop.interceptors.handlers;

import by.pzh.yandex.market.review.checker.aop.AOPUtils;
import by.pzh.yandex.market.review.checker.exceptions.EmptyFileException;
import by.pzh.yandex.market.review.checker.web.rest.validation.annotations.FileExtension;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.multipart.MultipartFile;

/**
 * File OAP interceptor.
 * Intercepts incoming requests which are marked with appropriate annotations and
 * checks whether file is empty.
 *
 * @author p.zhoidz
 */
@Aspect
public class NonEmptyFileInterceptor {
    /**
     * Test annotated file.
     * {@link EmptyFileException} is thrown if file is empty.
     *
     * @param joinPoint AOP join point.
     * @throws Throwable standard exception cases.
     */
    @Before("execution(* *(..,@by.pzh.yandex.market.review.checker.web.rest.validation.annotations.NonEmptyFile (*),..))")
    public void testFile(JoinPoint joinPoint) throws Throwable {

        AOPUtils.getWrappedParamsStream(joinPoint, FileExtension.class)
                .forEach(parameterWrapper -> {
                    MultipartFile file = (MultipartFile) joinPoint.getArgs()[parameterWrapper.getIndex()];
                    if (file.isEmpty()) {
                        throw new EmptyFileException();
                    }
                });

    }
}

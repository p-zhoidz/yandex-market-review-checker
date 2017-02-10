package by.pzh.yandex.market.review.checker.aop.interceptors.handlers;

import by.pzh.yandex.market.review.checker.aop.AOPUtils;
import by.pzh.yandex.market.review.checker.exceptions.IllegalFileTypeException;
import by.pzh.yandex.market.review.checker.web.rest.validation.annotations.FileExtension;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * File extension OAP interceptor.
 * Intercepts incoming requests which are marked with appropriate annotations and
 * checks file extension.
 *
 * @author p.zhoidz
 */
@Aspect
public class FileExtensionInterceptor {

    /**
     * Test file extension.
     * Throw {@link IllegalFileTypeException} if extension does not match expected.
     *
     * @param joinPoint AOP join point.
     * @throws Throwable standard exception cases.
     */
    @Before("execution(* *(..,@com.yoti.externalapp.reception.validation.annotations.FileExtension (*),..))")
    public void testFile(JoinPoint joinPoint) throws Throwable {

        AOPUtils.getWrappedParamsStream(joinPoint, FileExtension.class)
                .forEach(parameterWrapper -> {
                    MultipartFile file = (MultipartFile) joinPoint.getArgs()[parameterWrapper.getIndex()];
                    Parameter parameter = parameterWrapper.getParameter();
                    FileExtension annotation = parameter.getAnnotation(FileExtension.class);

                    List<String> extensions = Arrays.asList(annotation.extensions());
                    if (!extensions.contains(file.getContentType())) {
                        throw new IllegalFileTypeException();
                    }
                });

    }
}

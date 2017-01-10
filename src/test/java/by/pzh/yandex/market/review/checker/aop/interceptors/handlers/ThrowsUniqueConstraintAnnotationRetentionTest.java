package by.pzh.yandex.market.review.checker.aop.interceptors.handlers;

import by.pzh.yandex.market.review.checker.aop.interceptors.anootations.ThrowsUniqueConstraintException;
import com.google.common.reflect.ClassPath;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * @author p.zhoidz.
 */
public class ThrowsUniqueConstraintAnnotationRetentionTest {

    /**
     * Test method.
     * Expect correct usage of the {@link ThrowsUniqueConstraintException} annotation.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testAnnotationRetention() throws Exception {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        List<Method> result = ClassPath.from(loader).getAllClasses()
                .parallelStream()
                .filter(classInfo -> classInfo.getName().startsWith("by.pzh.yandex.market.review.checker"))
                .flatMap(classInfo -> Stream.of(classInfo.getName()))
                .map(aClass -> {
                    try {
                        return Class.forName(aClass);
                    } catch (ClassNotFoundException e) {
                        //spike
                        return String.class;
                    }
                })
                .flatMap(aClass -> Arrays.stream(aClass.getDeclaredMethods()))
                .filter(method -> method.isAnnotationPresent(ThrowsUniqueConstraintException.class))
                .filter(method -> !method.getReturnType().getName().equals(ResponseEntity.class.getName()))
                .collect(Collectors.toList());


        assertEquals("@UniqueConstraint should be used just for the methods which return "
                        + "ResponseEntity.class, but violations are found in the following methods",
                Collections.EMPTY_LIST, result);
    }
}

package by.pzh.yandex.market.review.checker.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * AOP utility class.
 *
 * @author p.zhoidz.
 */
public final class AOPUtils {
    /**
     * Private constructor.
     */
    private AOPUtils() {

    }

    /**
     * Parameter wrapper. Chains actual parameter value and its index (position) within method arguments.
     */
    public static final class ParameterWrapper {
        private int index;
        private Parameter parameter;

        /**
         * Parametrized constructor.
         *
         * @param index     index of the parameter within arguments list.
         * @param parameter actual parameter.
         */
        private ParameterWrapper(int index, Parameter parameter) {
            this.index = index;
            this.parameter = parameter;
        }

        /**
         * @return Returns the value of index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * @return Returns the value of parameter.
         */
        public Parameter getParameter() {
            return parameter;
        }
    }

    /**
     * Retrieve stream of the method parameters wrapped into {@link ParameterWrapper}.
     *
     * @param joinPoint      join point.
     * @param annotationType annotation type to search for.
     * @return stream of the method parameters.
     */
    public static Stream<ParameterWrapper> getWrappedParamsStream(JoinPoint joinPoint, Class annotationType) {
        AtomicInteger counter = new AtomicInteger();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        return Arrays.stream(signature.getMethod().getParameters())
                .map(parameter -> new ParameterWrapper(counter.getAndIncrement(), parameter))
                .filter(wrapper -> Arrays.stream(wrapper.getParameter().getAnnotations())
                        .filter(annotation ->
                                annotation.annotationType().equals(annotationType))
                        .count() > 0);
    }
}

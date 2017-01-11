package by.pzh.yandex.market.review.checker.platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Custom implementation of the eager object initialization.
 * Constraints:
 * 1. In order ot initialize object, it's field should be annotated by one o the following annotations:
 * - {@link OneToOne}
 * - {@link ManyToOne}
 * - {@link ManyToMany}
 * - {@link OneToMany}
 * <p>
 * 2. It should provide 'getter' for that field.
 *
 * @param <T>  Type of the entity.
 * @param <ID> Type of the identifier.
 * @author p.zhoidz.
 */
@Transactional
public interface EagerlyInitializablePagingRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID> {

    /**
     * Load entity eagerly. Just 1st level of relations.
     *
     * @param id Identifier.
     * @return Eagerly loaded entity.
     * @throws InvocationTargetException invocation exception.
     * @throws IllegalAccessException    Illegal access exception.
     * @throws NoSuchMethodException     No such method exception.
     */
    default T loadOneEagerly(ID id) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        T obj = findOne(id);

        if (obj != null) {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                List<Annotation> annotations = Arrays.asList(field.getAnnotations());
                List<Annotation> declaredAnnotations = annotations
                        .stream()
                        .filter(a -> a.annotationType().equals(ManyToOne.class)
                                || a.annotationType().equals(OneToOne.class)
                                || a.annotationType().equals(ManyToMany.class)
                                || a.annotationType().equals(OneToMany.class))
                        .collect(Collectors.toList());

                if (declaredAnnotations.size() > 0) {
                    //JavaBean convention getter method.
                    String methodName = "get" + field.getName().substring(0, 1).toUpperCase()
                            + field.getName().substring(1, field.getName().length());

                    Method declaredMethod = obj.getClass().getDeclaredMethod(methodName);
                    Object invoke = declaredMethod.invoke(obj);
                    if (invoke != null) {
                        //Object initialization
                        invoke.toString();
                    }
                }

            }
        }

        return obj;
    }

}
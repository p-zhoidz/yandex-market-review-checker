package by.pzh.yandex.market.review.checker.repository.specifications;

import by.pzh.yandex.market.review.checker.domain.Client_;
import by.pzh.yandex.market.review.checker.domain.Store_;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry_;
import by.pzh.yandex.market.review.checker.domain.Task_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.security.acl.Owner;
import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
public class TaskSpecifications {

    public static Specification<Task> filterById(Long id) {
        return (root, query, cb) ->
                cb.equal(root.get(Task_.id), id);
    }


    public static Specification<Task> fetchEntriesAndStore() {
        return (root, query, cb) -> {
            root.fetch(Task_.taskEntries, JoinType.LEFT)
                    .fetch(TaskEntry_.store, JoinType.LEFT);
            return cb.conjunction();
        };
    }

    public static Specification<Task> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.conjunction();
        };
    }

    public static Specification<Task> fetchPoster() {
        return (root, query, cb) -> {
            root.fetch(Task_.poster, JoinType.LEFT);
            return cb.conjunction();
        };
    }
}

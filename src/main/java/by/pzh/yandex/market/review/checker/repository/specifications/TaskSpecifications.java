package by.pzh.yandex.market.review.checker.repository.specifications;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry_;
import by.pzh.yandex.market.review.checker.domain.Task_;
import org.springframework.data.jpa.domain.Specification;

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
            root.fetch(Task_.taskEntries)
                    .fetch(TaskEntry_.store);
            return cb.conjunction();
        };
    }

    public static Specification<Task> fetchPoster() {
        return (root, query, cb) -> {
            root.fetch(Task_.poster);
            return cb.conjunction();
        };
    }
}

package by.pzh.yandex.market.review.checker.repository.specifications;

import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.TaskEntry_;
import by.pzh.yandex.market.review.checker.domain.Task_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

/**
 * @author p.zhoidz.
 */
public class TaskEntrySpecifications {


    public static Specification<TaskEntry> filterTaskId(Long taskId) {
        return (root, query, cb) ->
                cb.equal(root.join(TaskEntry_.task).get(Task_.id), taskId);
    }

    public static Specification<TaskEntry> fetchStore() {
        return (root, query, cb) -> {
            root.fetch(TaskEntry_.store, JoinType.LEFT);
            return cb.conjunction();
        };
    }
}

package by.pzh.yandex.market.review.checker.repository.specifications;

import by.pzh.yandex.market.review.checker.domain.Client_;
import by.pzh.yandex.market.review.checker.domain.Store_;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.TaskEntry_;
import by.pzh.yandex.market.review.checker.domain.Task_;
import by.pzh.yandex.market.review.checker.domain.enums.TaskEntryStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
public class TaskEntrySpecifications {

    public static Specification<TaskEntry> filterForClient(Long clientId) {
        return (root, query, cb) ->
                cb.equal(root
                        .join(TaskEntry_.store, JoinType.LEFT)
                        .join(Store_.owner,  JoinType.LEFT)
                        .get(Client_.id), clientId);
    }

    public static Specification<TaskEntry> endDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) ->
                cb.between(root.get(TaskEntry_.task).get(Task_.endDate), start, end);
    }


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

    public static Specification<TaskEntry> statusEquals(TaskEntryStatus ... taskEntryStatuses) {
        return (root, query, cb) ->
            root.get(TaskEntry_.status).in(taskEntryStatuses);

    }
}

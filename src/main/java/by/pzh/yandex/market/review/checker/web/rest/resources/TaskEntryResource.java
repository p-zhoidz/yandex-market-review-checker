package by.pzh.yandex.market.review.checker.web.rest.resources;

import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import lombok.Builder;
import lombok.Data;

/**
 * @author p.zhoidz.
 */
@Data
@Builder
public class TaskEntryResource extends CustomBaseResourceSupport {
    private Long number;
    private Store store;
    private Task task;

}

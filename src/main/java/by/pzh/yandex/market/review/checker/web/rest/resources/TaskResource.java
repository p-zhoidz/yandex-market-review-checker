package by.pzh.yandex.market.review.checker.web.rest.resources;

import by.pzh.yandex.market.review.checker.domain.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author p.zhoidz.
 */
@Data
@Builder
public class TaskResource extends CustomBaseResourceSupport {
    private Long number;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;
    private List<TaskEntryResource> taskEntries;
    private PosterResource poster;
    private TaskStatus status;
}

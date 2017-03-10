package by.pzh.yandex.market.review.checker.service.dto;

import by.pzh.yandex.market.review.checker.domain.enums.TaskEntryStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * A DTO for the TaskEntry entity.
 */
@Data
public class TaskEntryDTO implements Serializable {
    private Long id;

    @NotNull
    private StoreDTO store;

    @NotNull
    private Long taskId;

    private TaskEntryStatus status;
}

package by.pzh.yandex.market.review.checker.service.dto;

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
    private Long storeId;

    @NotNull
    private Long taskId;
}

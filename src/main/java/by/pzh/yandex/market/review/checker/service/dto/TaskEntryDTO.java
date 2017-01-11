package by.pzh.yandex.market.review.checker.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TaskEntry entity.
 */
@Data
public class TaskEntryDTO implements Serializable {
    private Long id;
    private Long storeId;
    private Long taskId;
}

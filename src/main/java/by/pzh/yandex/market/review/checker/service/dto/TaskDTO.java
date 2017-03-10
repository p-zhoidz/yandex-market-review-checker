package by.pzh.yandex.market.review.checker.service.dto;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.enums.TaskStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


/**
 * A DTO for the Task entity.
 */
@Data
public class TaskDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private String comment;

    @NotNull
    private Long posterId;

    private Poster poster;

    private TaskStatus status;

}

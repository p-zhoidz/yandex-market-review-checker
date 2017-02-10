package by.pzh.yandex.market.review.checker.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * A DTO for the Report entity.
 */
@Data
public class ReportDTO implements Serializable {
    private Long id;
    @NotNull
    private LocalDate date;
    @NotNull
    private Long taskId;
}

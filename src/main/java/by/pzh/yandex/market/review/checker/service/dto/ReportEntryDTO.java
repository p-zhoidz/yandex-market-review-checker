package by.pzh.yandex.market.review.checker.service.dto;

import by.pzh.yandex.market.review.checker.domain.enums.ReportStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the ReportEntry entity.
 */
@Data
public class ReportEntryDTO implements Serializable {
    private Long id;
    @NotNull
    private String text;
    @NotNull
    private ReportStatus status;
    @NotNull
    private Long reportId;
}

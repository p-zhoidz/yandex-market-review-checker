package by.pzh.yandex.market.review.checker.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author p.zhoidz.
 */
@Getter
@AllArgsConstructor
public class TaskGenerationDTO {
    private List<TaskDTO> tasks;
    private long total;
    private long required;

}

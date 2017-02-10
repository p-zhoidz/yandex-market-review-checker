package by.pzh.yandex.market.review.checker.service.dto;

import by.pzh.yandex.market.review.checker.domain.Poster;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
@Data
@Builder
public class CSVTaskDTO {
    String content;
    Poster poster;
    LocalDate periodStart;
    LocalDate periodEnd;

    public String getFileName() {
        StringBuilder builder = new StringBuilder();
        return builder.append(poster.getName())
                .append(" ")
                .append(poster.getEmail())
                .append(" ")
                .append(periodStart.toString())
                .append("-")
                .append(periodEnd.toString())
                .toString();
    }

}
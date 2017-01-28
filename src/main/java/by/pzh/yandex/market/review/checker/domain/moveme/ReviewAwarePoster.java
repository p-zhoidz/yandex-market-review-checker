package by.pzh.yandex.market.review.checker.domain.moveme;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author p.zhoidz.
 */
@Data
@AllArgsConstructor
public class ReviewAwarePoster {
    private long storeId;
    private long posterId;
    private LocalDate lastReview;

}

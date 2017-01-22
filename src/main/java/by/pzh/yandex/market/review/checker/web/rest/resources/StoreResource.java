package by.pzh.yandex.market.review.checker.web.rest.resources;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
@Data
public class StoreResource extends CustomBaseResourceSupport {
    private String url;
    private Boolean active;
    private Integer desiredReviewsNumber;
    private Long number;
    private LocalDate created;
}

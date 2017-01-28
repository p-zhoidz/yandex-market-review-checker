package by.pzh.yandex.market.review.checker.web.rest.resources;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
@Data
@Builder
public class PosterResource extends CustomBaseResourceSupport {
    private Long number;
    private String email;
    private String name;
    private Double rate;
    private Integer velocity;
    private Boolean active;
    private LocalDate created;
}

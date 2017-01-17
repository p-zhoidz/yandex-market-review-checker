package by.pzh.yandex.market.review.checker.web.rest.resources;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
@Data
public class ClientResource extends ResourceSupport {
    private String email;
    private Boolean active;
    private String comment;
    private Long number;
    private LocalDate creationDate;

}

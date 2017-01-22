package by.pzh.yandex.market.review.checker.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * A DTO for the Store entity.
 */
@Data
public class StoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    @NotNull
    private Boolean active;

    @NotNull
    private Integer desiredReviewsNumber;

    private Long ownerId;

    private LocalDate created;

}

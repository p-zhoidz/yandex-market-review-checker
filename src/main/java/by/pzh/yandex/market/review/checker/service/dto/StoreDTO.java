package by.pzh.yandex.market.review.checker.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Store entity.
 */
@Data
public class StoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String storeUrl;

    private Boolean active;

    @NotNull
    private Integer desiredReviewsNumber;

    @NotNull
    private Long ownerId;

}

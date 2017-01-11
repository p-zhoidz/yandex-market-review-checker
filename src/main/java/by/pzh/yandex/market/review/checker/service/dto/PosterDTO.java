package by.pzh.yandex.market.review.checker.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Poster entity.
 */
@Data
public class PosterDTO implements Serializable {

    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private Double rate;

    @NotNull
    private Integer capacity;

    private Boolean active;

}

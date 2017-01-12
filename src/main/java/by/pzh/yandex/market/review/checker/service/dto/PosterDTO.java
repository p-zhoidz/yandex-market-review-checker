package by.pzh.yandex.market.review.checker.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Poster entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

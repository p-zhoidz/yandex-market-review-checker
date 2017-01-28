package by.pzh.yandex.market.review.checker.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * A DTO for the Poster entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosterDTO implements Serializable {

    private Long number;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private Double rate;

    @NotNull
    private Integer velocity;

    private Boolean active;

    private LocalDate created;

}

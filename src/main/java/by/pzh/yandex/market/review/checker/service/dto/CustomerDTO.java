package by.pzh.yandex.market.review.checker.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * A DTO for the Customer entity.
 */
@Data
public class CustomerDTO implements Serializable {
    private Long id;

    @NotNull
    private String email;
    private Boolean active;
}

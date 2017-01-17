
package by.pzh.yandex.market.review.checker.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * A DTO for the Client entity.
 */

@Data
public class ClientDTO implements Serializable {

    private Long number;
    @NotNull
    private String email;
    @NotNull
    private Boolean active;
    private LocalDate creationDate;
    private String comment;
}


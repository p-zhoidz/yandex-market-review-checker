
package by.pzh.yandex.market.review.checker.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * A DTO for the Client entity.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO implements Serializable {

    private Long number;
    @NotNull
    private String email;
    @NotNull
    private Boolean active;
    private LocalDate created;
    private String comment;
}


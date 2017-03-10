package by.pzh.yandex.market.review.checker.domain;


import by.pzh.yandex.market.review.checker.domain.features.AbstractAuditingEntity;
import by.pzh.yandex.market.review.checker.domain.moveme.ReviewAwarePoster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Poster.
 */
@SqlResultSetMapping(name = "posterStoreStatistics", classes =
@ConstructorResult(
        targetClass = ReviewAwarePoster.class,
        columns = {
                @ColumnResult(name = "store_id", type = long.class),
                @ColumnResult(name = "poster_id", type = long.class),
                @ColumnResult(name = "end_date", type = LocalDate.class)
        })
)

@Entity
@Table(name = "posters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poster extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @AccessType(AccessType.Type.PROPERTY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Double rate;

    @NotNull
    @Column(name = "velocity", nullable = false)
    private Integer velocity;

    @Column(name = "active")
    private Boolean active;

}

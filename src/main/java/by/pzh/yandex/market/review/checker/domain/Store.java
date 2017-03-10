package by.pzh.yandex.market.review.checker.domain;


import by.pzh.yandex.market.review.checker.domain.features.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Store.
 */
@Entity
@Table(name = "stores")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "owner")
public class Store extends AbstractAuditingEntity implements  Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Long id;

    @NotNull
    @Column(name = "store_url", nullable = false)
    private String url;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "desired_reviews_number", nullable = false)
    private Integer desiredReviewsNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client owner;

}

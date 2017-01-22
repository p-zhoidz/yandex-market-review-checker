package by.pzh.yandex.market.review.checker.domain.features;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractAuditingEntity {

    @CreatedDate
    @Column(name = "creation_date", updatable = false)
    protected LocalDate created;
}

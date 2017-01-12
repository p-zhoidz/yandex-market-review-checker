package by.pzh.yandex.market.review.checker.domain;


import by.pzh.yandex.market.review.checker.domain.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A ReportEntry.
 */
@Entity
@Table(name = "report_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus status;

    @ManyToOne
    private Report report;

}

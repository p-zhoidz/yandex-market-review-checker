package by.pzh.yandex.market.review.checker.domain;


import by.pzh.yandex.market.review.checker.domain.enums.TaskEntryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A TaskEntry.
 */
@Entity
@Table(name = "task_entries")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskEntryStatus status;

    @Lob
    @Column
    private String text;

}

package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data JPA repository for the TaskEntry entity.
 */
@SuppressWarnings("unused")
public interface TaskEntryRepository extends JpaRepository<TaskEntry, Long>, JpaSpecificationExecutor<TaskEntry> {

}

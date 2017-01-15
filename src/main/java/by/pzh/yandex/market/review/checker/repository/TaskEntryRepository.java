package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TaskEntry entity.
 */
@SuppressWarnings("unused")
public interface TaskEntryRepository extends JpaRepository<TaskEntry, Long> {

}

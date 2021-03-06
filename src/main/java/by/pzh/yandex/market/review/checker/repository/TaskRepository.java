package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
public interface TaskRepository extends JpaRepository<Task, Long> {

}

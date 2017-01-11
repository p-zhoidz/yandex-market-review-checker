package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Report entity.
 */
@SuppressWarnings("unused")
public interface ReportRepository extends JpaRepository<Report, Long> {

}

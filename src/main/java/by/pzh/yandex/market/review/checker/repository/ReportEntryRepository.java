package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ReportEntry entity.
 */
@SuppressWarnings("unused")
public interface ReportEntryRepository extends JpaRepository<ReportEntry, Long> {

}

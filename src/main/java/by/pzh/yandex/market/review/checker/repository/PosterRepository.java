package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.Poster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Poster entity.
 */
@SuppressWarnings("unused")
public interface PosterRepository extends JpaRepository<Poster, Long> {

}

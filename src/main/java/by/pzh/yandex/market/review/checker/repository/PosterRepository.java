package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.repository.impl.PosterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data JPA repository for the Poster entity.
 */
@SuppressWarnings("unused")
public interface PosterRepository extends JpaRepository<Poster, Long>,
        JpaSpecificationExecutor<Poster>,
        PosterRepositoryCustom {

}

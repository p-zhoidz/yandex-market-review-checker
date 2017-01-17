package by.pzh.yandex.market.review.checker.repository;

import by.pzh.yandex.market.review.checker.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data JPA repository for the Store entity.
 */
@SuppressWarnings("unused")
public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {

}

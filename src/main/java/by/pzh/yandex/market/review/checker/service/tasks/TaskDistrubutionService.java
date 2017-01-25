package by.pzh.yandex.market.review.checker.service.tasks;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.repository.StoreRepository;
import by.pzh.yandex.market.review.checker.repository.specifications.PosterSpecifications;
import by.pzh.yandex.market.review.checker.repository.specifications.StoreSpecifications;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author p.zhoidz.
 */
@Component
public class TaskDistrubutionService {

    @Inject
    private PosterRepository posterRepository;

    @Inject
    private StoreRepository storeRepository;

    public List<Task> distribute() {

        // 1. Find all stores
        //FIXME think of pagination
        List<Store> stores = storeRepository.findAll(where(StoreSpecifications.filterActive()));

        // 2. Find all posters
        //FIXME think of pagination
        List<Poster> posters = posterRepository.findAll(where(PosterSpecifications.filterActive()));


        // 3. Find latest entry by store/poster

        return null;
    }
}

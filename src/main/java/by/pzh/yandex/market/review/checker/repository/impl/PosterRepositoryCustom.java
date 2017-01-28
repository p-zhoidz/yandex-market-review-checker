package by.pzh.yandex.market.review.checker.repository.impl;

import by.pzh.yandex.market.review.checker.service.tasks.TaskDistributionService;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @author p.zhoidz.
 */
@NoRepositoryBean
public interface PosterRepositoryCustom {

    /**
     * Retrieve all posters/stores combinations, which also contains the latest review date.
     *
     * @return List of
     * {@link by.pzh.yandex.market.review.checker.service.tasks.TaskDistributionService.PosterDataHolder}
     */
    List<TaskDistributionService.PosterDataHolder> getPosters();
}

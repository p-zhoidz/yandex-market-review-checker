package by.pzh.yandex.market.review.checker.repository.impl;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.moveme.ReviewAwarePoster;
import by.pzh.yandex.market.review.checker.service.tasks.TaskDistributionService;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author p.zhoidz.
 */
@Repository
public class PosterRepositoryImpl implements PosterRepositoryCustom {

    @Inject
    private EntityManager entityManager;

    private final String QUERY =
            "SELECT entry.store_id, entry.poster_id, res.end_date FROM ( "
                    + "SELECT stores.id AS store_id, posters.id AS poster_id "
                    + "FROM stores , posters  WHERE posters.active = TRUE) entry "
                    + "LEFT JOIN ( "
                    + "SELECT end_date, store_id, poster_id FROM report_entries re "
                    + "LEFT JOIN task_entries te_o ON re.task_entry_id = te_o.id "
                    + "LEFT JOIN  tasks t_o ON te_o.task_id = t_o.id "
                    + "WHERE "
                    + "re.task_entry_id IN (SELECT MAX(te.id) FROM task_entries te LEFT JOIN tasks t "
                    + "ON te.task_id = t.id GROUP BY te.store_id, t.poster_id) "
                    + ") res "
                    + "ON  res.store_id = entry.store_id AND  res.poster_id = entry.poster_id";


    @Override
    public List<TaskDistributionService.PosterDataHolder> getPosters() {
        List<ReviewAwarePoster> statistics = entityManager
                .createNativeQuery(QUERY, "posterStoreStatistics")
                .getResultList();

        return statistics.stream()
                .collect(groupingBy(ReviewAwarePoster::getPosterId))
                .entrySet()
                .stream()
                .map(e ->
                        new TaskDistributionService.PosterDataHolder(
                                entityManager.find(Poster.class, e.getKey()),
                                e.getValue()
                                        .stream()
                                        .collect(toMap(
                                                ReviewAwarePoster::getStoreId,
                                                rap -> rap.getLastReview() != null ? rap.getLastReview()
                                                        : LocalDate.ofEpochDay(0),
                                                (localDate, localDate2) -> localDate))
                        )).collect(toList());
    }

}

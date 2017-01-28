package by.pzh.yandex.market.review.checker.service.tasks;

import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.repository.StoreRepository;
import by.pzh.yandex.market.review.checker.repository.specifications.StoreSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author p.zhoidz.
 */
@Component
public class TaskDistributionService {
    private PosterRepository posterRepository;
    private StoreRepository storeRepository;

    /**
     * Parametrized constructor.
     *
     * @param posterRepository poster repository instance.
     * @param storeRepository  store Repository instance.
     */
    public TaskDistributionService(PosterRepository posterRepository, StoreRepository storeRepository) {
        this.posterRepository = posterRepository;
        this.storeRepository = storeRepository;
    }

    /**
     * Run task distribution process.
     *
     * @return List of distributed tasks.
     */
    public List<Task> distribute() {

        List<Store> stores = storeRepository.findAll(where(StoreSpecifications.filterActive()));
        List<PosterDataHolder> posters = posterRepository.getPosters();

        return stores
                .parallelStream()
                .map(store -> getReviewRequests(store, posters, store.getDesiredReviewsNumber()))
                .flatMap(Collection::stream)
                .collect(groupingBy(o -> Task.builder()
                                .poster(o.poster)
                                .build(),
                        HashMap::new,
                        mapping(o -> TaskEntry.builder()
                                        .store(o.store)
                                        .build(),
                                toList())))
                .entrySet()
                .stream()
                .map(e -> {
                    e.getValue()
                            .forEach(taskEntry -> taskEntry.setTask(e.getKey()));
                    return e;
                }).map(e -> {
                    e.getKey().setTaskEntries(e.getValue());
                    return e.getKey();
                }).collect(toList());
    }


    /**
     * Recursively distribute tasks across posters.
     * Method can be called recursively in case of need.
     *
     * @param store   store to generate tasks for.
     * @param posters posters info.
     * @param limit   number of needed reviews for the store.
     * @return list of {@link ReviewRequest}.
     */
    private List<ReviewRequest> getReviewRequests(Store store, List<PosterDataHolder> posters, int limit) {

        List<PosterDataHolder> tempResult = posters.stream()
                .sorted(((Comparator<PosterDataHolder>) (o1, o2) -> o1.poster.getRate()
                        .compareTo(o2.poster.getRate()))
                        .thenComparing((o1, o2) -> o1.reviews.get(store.getId())
                                .compareTo(o2.reviews.get(store.getId()))))
                .collect(toList())
                .stream()
                .filter(posterDataHolder -> posterDataHolder.velocity.getAndDecrement() >= 0)
                .limit(limit)
                .collect(toList());

        List<ReviewRequest> mainResult = tempResult
                .stream()
                .map(holder -> new ReviewRequest(store, holder.poster))
                .collect(toList());

        if (tempResult.size() > 0 && tempResult.size() < store.getDesiredReviewsNumber()) {
            List<ReviewRequest> reviewRequests = getReviewRequests(store, tempResult, limit - tempResult.size());
            mainResult.addAll(reviewRequests);
        }

        return mainResult;
    }


    /**
     * Review request operation class.
     */
    @AllArgsConstructor
    private static class ReviewRequest {
        private Store store;
        private Poster poster;
    }

    /**
     * Holder class for poster related info.
     */
    public static class PosterDataHolder {
        private Poster poster;
        private Map<Long, LocalDate> reviews;
        private AtomicInteger velocity;


        /**
         * Parametrized constructor.
         *
         * @param poster  Poster instance.
         * @param reviews store/last review date map.
         */
        public PosterDataHolder(Poster poster, Map<Long, LocalDate> reviews) {
            this.poster = poster;
            this.reviews = reviews;
            this.velocity = new AtomicInteger(poster.getVelocity());
        }

    }


}
package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;

import java.time.LocalDate;

/**
 * @author p.zhoidz.
 */
public final class TestDummyObjectsFactory {

    private TestDummyObjectsFactory() {
    }

    public static Poster getPoster() {
        return Poster.builder()
                .active(Boolean.TRUE)
                .capacity(2)
                .email("email@test.com")
                .name("Test Name")
                .rate(32.0)
                .build();
    }

    public static Report getReport() {
        return Report.builder()
                .date(LocalDate.now())
                .poster(getPoster())
                .build();
    }

    public static Client getCustomer() {
        return Client.builder()
                .active(Boolean.TRUE)
                .email("test@emai.com")
                .build();
    }

    public static Task getTask() {
        return Task.builder()
                .poster(getPoster())
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();
    }

    public static Store getStore() {
        return Store.builder()
                .storeUrl("url")
                .active(Boolean.TRUE)
                .desiredReviewsNumber(3)
                .owner(getCustomer())
                .build();
    }
}

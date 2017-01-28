package by.pzh.yandex.market.review.checker.service.tasks;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author p.zhoidz.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
@Transactional
public class TaskDistributionServiceIntegrationTest {

    @Inject
    private TaskDistributionService sut;

    @Inject
    private EntityManager em;


    @Test
    @Transactional
    public void testTwoPostersOneStoreEachGotTask() {
        Poster poster1 = Poster.builder()
                .active(true)
                .email("test")
                .name("name")
                .rate(34.0)
                .velocity(14)
                .build();

        Poster poster2 = Poster.builder()
                .active(true)
                .email("test2")
                .name("name")
                .rate(31.0)
                .velocity(2)
                .build();

        Client client = Client.builder()
                .email("c1")
                .active(true)
                .build();

        Store store = Store.builder()
                .active(true)
                .desiredReviewsNumber(3)
                .owner(client)
                .url("url1")
                .build();

        em.persist(poster1);
        em.persist(poster2);
        em.persist(client);
        em.persist(store);

        List<Task> result = sut.distribute();

        Optional<Task> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPoster().getId().equals(poster1.getId()))
                .findFirst();

        Optional<Task> secondPosterTaskOpt = result.stream()
                .filter(e -> e.getPoster().getId().equals(poster2.getId()))
                .findFirst();

        assertEquals(2, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        Task task = firstPosterTaskOpt.get();
        assertEquals(1, task.getTaskEntries().size());

        assertTrue(secondPosterTaskOpt.isPresent());
        Task task2 = secondPosterTaskOpt.get();
        assertEquals(2, task2.getTaskEntries().size());
    }

    @Test
    @Transactional
    public void testOnePosterOneStoreAllToOne() {
        Poster poster1 = Poster.builder()
                .active(true)
                .email("test")
                .name("name")
                .rate(34.0)
                .velocity(14)
                .build();

        Client client = Client.builder()
                .email("c1")
                .active(true)
                .build();

        Store store = Store.builder()
                .active(true)
                .desiredReviewsNumber(4)
                .owner(client)
                .url("url1")
                .build();

        em.persist(poster1);
        em.persist(client);
        em.persist(store);

        List<Task> result = sut.distribute();

        Optional<Task> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPoster().getId().equals(poster1.getId()))
                .findFirst();

        assertEquals(1, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        Task task = firstPosterTaskOpt.get();
        assertEquals(4, task.getTaskEntries().size());
    }

    @Test
    @Transactional
    public void testNoPosterOneStoreNoTasks() {
        Client client = Client.builder()
                .email("c1")
                .active(true)
                .build();

        Store store = Store.builder()
                .active(true)
                .desiredReviewsNumber(3)
                .owner(client)
                .url("url1")
                .build();

        em.persist(client);
        em.persist(store);

        List<Task> result = sut.distribute();
        assertEquals(0, result.size());
    }

    @Test
    @Transactional
    public void testTwoPostersOneDisabledOneStoreAllToOne() {
        Poster poster1 = Poster.builder()
                .active(true)
                .email("test")
                .name("name")
                .rate(34.0)
                .velocity(14)
                .build();

        Poster poster2 = Poster.builder()
                .active(false)
                .email("test2")
                .name("name")
                .rate(31.0)
                .velocity(2)
                .build();

        Client client = Client.builder()
                .email("c1")
                .active(true)
                .build();

        Store store = Store.builder()
                .active(true)
                .desiredReviewsNumber(4)
                .owner(client)
                .url("url1")
                .build();

        em.persist(poster1);
        em.persist(poster2);
        em.persist(client);
        em.persist(store);

        List<Task> result = sut.distribute();

        Optional<Task> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPoster().getId().equals(poster1.getId()))
                .findFirst();

        Optional<Task> secondPosterTaskOpt = result.stream()
                .filter(e -> e.getPoster().getId().equals(poster2.getId()))
                .findFirst();

        assertEquals(1, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        assertFalse(secondPosterTaskOpt.isPresent());
        Task task = firstPosterTaskOpt.get();
        assertEquals(4, task.getTaskEntries().size());
    }

    @Test
    @Transactional
    public void testOnePosterTwoStoreOneDisabledAllToOne() {
        Poster poster1 = Poster.builder()
                .active(true)
                .email("test")
                .name("name")
                .rate(34.0)
                .velocity(14)
                .build();

        Client client = Client.builder()
                .email("c1")
                .active(true)
                .build();

        Store store1 = Store.builder()
                .active(true)
                .desiredReviewsNumber(4)
                .owner(client)
                .url("url1")
                .build();

        Store store2 = Store.builder()
                .active(false)
                .desiredReviewsNumber(4)
                .owner(client)
                .url("url2")
                .build();

        em.persist(poster1);
        em.persist(client);
        em.persist(store1);
        em.persist(store2);

        List<Task> result = sut.distribute();

        Optional<Task> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPoster().getId().equals(poster1.getId()))
                .findFirst();

        assertEquals(1, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        Task task = firstPosterTaskOpt.get();
        task.getTaskEntries().forEach(te -> assertEquals(store1.getId(), te.getStore().getId()));

        assertEquals(4, task.getTaskEntries().size());
    }

}
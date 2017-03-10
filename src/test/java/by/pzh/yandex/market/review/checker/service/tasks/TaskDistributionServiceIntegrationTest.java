package by.pzh.yandex.market.review.checker.service.tasks;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.repository.StoreRepository;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import by.pzh.yandex.market.review.checker.repository.TaskRepository;
import by.pzh.yandex.market.review.checker.service.dto.TaskDTO;
import by.pzh.yandex.market.review.checker.service.dto.TaskGenerationDTO;
import by.pzh.yandex.market.review.checker.service.mappers.TaskMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.filterTaskId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author p.zhoidz.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
@Transactional
public class TaskDistributionServiceIntegrationTest {

    @Inject
    private PosterRepository posterRepository;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private TaskEntryRepository taskEntryRepository;

    @Inject
    private TaskMapper taskMapper;

    @Inject
    private EntityManager em;

    private TaskDistributionService sut;

    @Before
    public void setUp() throws Exception {
        sut = new TaskDistributionService(posterRepository, storeRepository, taskRepository,
                taskEntryRepository, taskMapper);
    }

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

        TaskGenerationDTO distributionResult = sut.distribute();
        List<TaskDTO> result = distributionResult.getTasks();

        Optional<TaskDTO> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPosterId().equals(poster1.getId()))
                .findFirst();

        Optional<TaskDTO> secondPosterTaskOpt = result.stream()
                .filter(e -> e.getPosterId().equals(poster2.getId()))
                .findFirst();

        assertEquals(2, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        TaskDTO task = firstPosterTaskOpt.get();

        List<TaskEntry> taskEntries1 = taskEntryRepository
                .findAll(where(filterTaskId(task.getId())));

        assertEquals(1, taskEntries1.size());

        assertTrue(secondPosterTaskOpt.isPresent());
        TaskDTO task2 = secondPosterTaskOpt.get();

        List<TaskEntry> taskEntries2 = taskEntryRepository
                .findAll(where(filterTaskId(task2.getId())));

        assertEquals(2, taskEntries2.size());

        assertEquals(3, distributionResult.getRequired());
        assertEquals(3, distributionResult.getTotal());
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

        TaskGenerationDTO distributionResult = sut.distribute();
        List<TaskDTO> result = distributionResult.getTasks();

        Optional<TaskDTO> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPosterId().equals(poster1.getId()))
                .findFirst();

        assertEquals(1, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        TaskDTO task = firstPosterTaskOpt.get();

        List<TaskEntry> taskEntries = taskEntryRepository
                .findAll(where(filterTaskId(task.getId())));
        assertEquals(4, taskEntries.size());

        assertEquals(4, distributionResult.getRequired());
        assertEquals(4, distributionResult.getTotal());
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

        TaskGenerationDTO result = sut.distribute();
        assertEquals(0, result.getTasks().size());
        assertEquals(3, result.getRequired());
        assertEquals(0, result.getTotal());
    }

    @Test
    @Transactional
    public void testTwoPostersOneDisabledOneStoreAllToOne() {
        Poster poster1 = Poster.builder()
                .active(true)
                .email("test")
                .name("name")
                .rate(34.0)
                .velocity(2)
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

        TaskGenerationDTO distributionResult = sut.distribute();
        List<TaskDTO> result = distributionResult.getTasks();

        Optional<TaskDTO> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPosterId().equals(poster1.getId()))
                .findFirst();

        Optional<TaskDTO> secondPosterTaskOpt = result.stream()
                .filter(e -> e.getPosterId().equals(poster2.getId()))
                .findFirst();

        assertEquals(1, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        assertFalse(secondPosterTaskOpt.isPresent());
        TaskDTO task = firstPosterTaskOpt.get();

        List<TaskEntry> taskEntries = taskEntryRepository
                .findAll(where(filterTaskId(task.getId())));

        assertEquals(2, taskEntries.size());

        assertEquals(4, distributionResult.getRequired());
        assertEquals(2, distributionResult.getTotal());
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

        TaskGenerationDTO distributionResult = sut.distribute();

        List<TaskDTO> result = distributionResult.getTasks();

        Optional<TaskDTO> firstPosterTaskOpt = result.stream()
                .filter(e -> e.getPosterId().equals(poster1.getId()))
                .findFirst();

        assertEquals(1, result.size());
        assertTrue(firstPosterTaskOpt.isPresent());
        TaskDTO task = firstPosterTaskOpt.get();

        List<TaskEntry> taskEntries = taskEntryRepository
                .findAll(where(filterTaskId(task.getId())));


        taskEntries.forEach(te -> assertEquals(store1.getId(), te.getStore().getId()));

        assertEquals(4, taskEntries.size());

        assertEquals(4, distributionResult.getRequired());
        assertEquals(4, distributionResult.getTotal());
    }

}
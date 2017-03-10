package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.enums.TaskEntryStatus;
import by.pzh.yandex.market.review.checker.domain.enums.TaskStatus;
import by.pzh.yandex.market.review.checker.service.impl.CSVService;
import by.pzh.yandex.market.review.checker.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author p.zhoidz.
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
public class ReportingControllerSpringTest {

    @Inject
    private CSVService csvService;

    @Inject
    private ExceptionTranslator exceptionTranslator;

    @Inject
    private EntityManager em;

    private MockMvc restMockMvc;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReportingController reportingController = new ReportingController(csvService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(reportingController)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    public void testCSVTask() throws Exception {
        Poster poster = Poster.builder()
                .velocity(1)
                .rate(12.0)
                .name("Test name")
                .email("Test email")
                .active(Boolean.TRUE)
                .build();

        em.persist(poster);

        Task task = Task.builder()
                .startDate(LocalDate.of(2017, 1, 1))
                .endDate(LocalDate.of(2017, 1, 7))
                .poster(poster)
                .status(TaskStatus.OPEN)
                .build();

        Client client = Client.builder()
                .active(Boolean.TRUE)
                .email("client@email.com")
                .build();

        Store store = Store.builder()
                .owner(client)
                .active(Boolean.TRUE)
                .desiredReviewsNumber(2)
                .url("sample url")
                .build();

        TaskEntry taskEntry1
                = TaskEntry.builder()
                .store(store)
                .task(task)
                .status(TaskEntryStatus.CONFIRMED)
                .build();

        TaskEntry taskEntry2
                = TaskEntry.builder()
                .store(store)
                .task(task)
                .status(TaskEntryStatus.CONFIRMED)
                .build();

        em.persist(client);
        em.persist(store);
        em.persist(task);
        em.persist(taskEntry1);
        em.persist(taskEntry2);

        em.flush();
        em.clear();

        String contentAsString = restMockMvc.perform(get("/api/reports/tasks/{id}/task", task.getId()))
                .andExpect(status().isOk())
                .andExpect(header().stringValues(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"Test name Test email 2017-01-01-2017-01-07\""))
                .andExpect(content().contentType("text/csv; charset=UTF-8"))
                .andExpect(content().encoding("UTF-8"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(contentAsString.matches("\"#\",URL Магазина,Текст\n" +
                "\\d+,sample url\n" +
                "\\d+,sample url\n"));
    }
}
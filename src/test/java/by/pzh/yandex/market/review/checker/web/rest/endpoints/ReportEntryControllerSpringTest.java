package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.enums.ReportStatus;
import by.pzh.yandex.market.review.checker.repository.ReportEntryRepository;
import by.pzh.yandex.market.review.checker.service.dto.ReportEntryDTO;
import by.pzh.yandex.market.review.checker.service.impl.ReportEntryService;
import by.pzh.yandex.market.review.checker.service.mappers.ReportEntryMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the ReportEntryResource REST controller.
 *
 * @see ReportEntryController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
public class ReportEntryControllerSpringTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final ReportStatus DEFAULT_STATUS = ReportStatus.NOT_VERIFIED;
    private static final ReportStatus UPDATED_STATUS = ReportStatus.VERIFIED;

    @Inject
    private ReportEntryService reportEntryService;

    @Inject
    private ReportEntryRepository reportEntryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReportEntryMockMvc;

    private ReportEntry reportEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReportEntryController reportEntryResource = new ReportEntryController();
        ReflectionTestUtils.setField(reportEntryResource, "reportEntryService", reportEntryService);
        this.restReportEntryMockMvc = MockMvcBuilders.standaloneSetup(reportEntryResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportEntry createEntity(EntityManager em) {
        Report report = TestDummyObjectsFactory.getReport();
        Task task = report.getTask();
        em.persist(task.getPoster());
        em.persist(task);
        em.persist(report);

        return ReportEntry.builder()
                .text(DEFAULT_TEXT)
                .status(DEFAULT_STATUS)
                .report(report)
                .build();
    }

    @Before
    public void initTest() {
        reportEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllReportEntries() throws Exception {
        // Initialize the database
        reportEntryRepository.saveAndFlush(reportEntry);

        // Get all the reportEntryList
        restReportEntryMockMvc.perform(get("/api/report-entries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reportEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getReportEntry() throws Exception {
        // Initialize the database
        reportEntryRepository.saveAndFlush(reportEntry);

        // Get the reportEntry
        restReportEntryMockMvc.perform(get("/api/report-entries/{id}", reportEntry.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(reportEntry.getId().intValue()))
                .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
                .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReportEntry() throws Exception {
        // Get the reportEntry
        restReportEntryMockMvc.perform(get("/api/report-entries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteReportEntry() throws Exception {
        // Initialize the database
        reportEntryRepository.saveAndFlush(reportEntry);
        int databaseSizeBeforeDelete = reportEntryRepository.findAll().size();

        // Get the reportEntry
        restReportEntryMockMvc.perform(delete("/api/report-entries/{id}", reportEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ReportEntry> reportEntryList = reportEntryRepository.findAll();
        assertThat(reportEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

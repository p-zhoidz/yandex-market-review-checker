package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.enums.TaskEntryStatus;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import by.pzh.yandex.market.review.checker.service.dto.TaskEntryDTO;
import by.pzh.yandex.market.review.checker.service.impl.TaskEntryService;
import by.pzh.yandex.market.review.checker.service.mappers.TaskEntryMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the TaskEntryResource REST controller.
 *
 * @see TaskEntryController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
public class TaskEntryControllerSpringTest {

    @Inject
    private TaskEntryService taskEntryService;

    @Inject
    private TaskEntryRepository taskEntryRepository;

    @Inject
    private TaskEntryMapper taskEntryMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTaskEntryMockMvc;

    private TaskEntry taskEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaskEntryController sut = new TaskEntryController(taskEntryService);
        this.restTaskEntryMockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskEntry createEntity(EntityManager em) {
        Task task = TestDummyObjectsFactory.getTask();
        Store store = TestDummyObjectsFactory.getStore();

        em.persist(task.getPoster());
        em.persist(task);
        em.persist(store.getOwner());
        em.persist(store);

        return TaskEntry.builder()
                .task(task)
                .store(store)
                .status(TaskEntryStatus.CONFIRMED)
                .build();
    }

    @Before
    public void initTest() {
        taskEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskEntry() throws Exception {
        int databaseSizeBeforeCreate = taskEntryRepository.findAll().size();

        // Create the TaskEntry
        TaskEntryDTO taskEntryDTO = taskEntryMapper.taskEntryToTaskEntryDTO(taskEntry);

        restTaskEntryMockMvc.perform(post("/api/task-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taskEntryDTO)))
                .andExpect(status().isCreated());

        // Validate the TaskEntry in the database
        List<TaskEntry> taskEntryList = taskEntryRepository.findAll();
        assertThat(taskEntryList).hasSize(databaseSizeBeforeCreate + 1);
        TaskEntry testTaskEntry = taskEntryList.get(taskEntryList.size() - 1);
    }

    @Test
    @Transactional
    public void createTaskEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskEntryRepository.findAll().size();

        // Create the TaskEntry with an existing ID
        TaskEntry existingTaskEntry = new TaskEntry();
        existingTaskEntry.setId(1L);
        existingTaskEntry.setTask(taskEntry.getTask());
        existingTaskEntry.setStore(taskEntry.getStore());
        existingTaskEntry.setStatus(TaskEntryStatus.CONFIRMED);
        TaskEntryDTO existingTaskEntryDTO = taskEntryMapper.taskEntryToTaskEntryDTO(existingTaskEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskEntryMockMvc.perform(post("/api/task-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingTaskEntryDTO)))
                .andExpect(status().isCreated());

        // Validate the Alice in the database
        List<TaskEntry> taskEntryList = taskEntryRepository.findAll();
        assertThat(taskEntryList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    public void getTaskEntry() throws Exception {
        // Initialize the database
        taskEntryRepository.saveAndFlush(taskEntry);

        // Get the taskEntry
        restTaskEntryMockMvc.perform(get("/api/task-entries/{id}", taskEntry.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(taskEntry.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaskEntry() throws Exception {
        // Get the taskEntry
        restTaskEntryMockMvc.perform(get("/api/task-entries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteTaskEntry() throws Exception {
        // Initialize the database
        taskEntryRepository.saveAndFlush(taskEntry);
        int databaseSizeBeforeDelete = taskEntryRepository.findAll().size();

        // Get the taskEntry
        restTaskEntryMockMvc.perform(delete("/api/task-entries/{id}", taskEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskEntry> taskEntryList = taskEntryRepository.findAll();
        assertThat(taskEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

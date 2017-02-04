package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.domain.Store;
import by.pzh.yandex.market.review.checker.repository.StoreRepository;
import by.pzh.yandex.market.review.checker.service.dto.StoreDTO;
import by.pzh.yandex.market.review.checker.service.impl.StoreService;
import by.pzh.yandex.market.review.checker.service.mappers.StoreMapper;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.StoreResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the StoreResource REST controller.
 *
 * @see StoreController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
public class StoreControllerSpringTest {

    private static final String DEFAULT_STORE_URL = "AAAAAAAAAA";
    private static final String UPDATED_STORE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Integer DEFAULT_DESIRED_REVIEWS_NUMBER = 1;
    private static final Integer UPDATED_DESIRED_REVIEWS_NUMBER = 2;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreMapper storeMapper;

    @Inject
    private StoreService storeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private ExceptionTranslator exceptionTranslator;

    @Inject
    private EntityManager em;

    private MockMvc restStoreMockMvc;

    private Store store;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoreController storeResource = new StoreController(storeService);
        this.restStoreMockMvc = MockMvcBuilders.standaloneSetup(storeResource)
                .setControllerAdvice(exceptionTranslator)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createEntity(EntityManager em) {
        Client client = TestDummyObjectsFactory.getCustomer();
        em.persist(client);

        Store store = Store.builder()
                .owner(client)
                .url(DEFAULT_STORE_URL)
                .active(DEFAULT_ACTIVE)
                .desiredReviewsNumber(DEFAULT_DESIRED_REVIEWS_NUMBER)
                .build();
        return store;
    }

    @Before
    public void initTest() {
        store = createEntity(em);
    }

    @Test
    @Transactional
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store);

        restStoreMockMvc.perform(post("/api/clients/{client-id}/stores", store.getOwner().getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
                .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getUrl()).isEqualTo(DEFAULT_STORE_URL);
        assertThat(testStore.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testStore.getDesiredReviewsNumber()).isEqualTo(DEFAULT_DESIRED_REVIEWS_NUMBER);
    }

    @Test
    @Transactional
    public void checkStoreUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setUrl(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store);

        restStoreMockMvc.perform(post("/api/clients/{client-id}/stores", store.getOwner().getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesiredReviewsNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setDesiredReviewsNumber(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store);
        restStoreMockMvc.perform(post("/api/clients/{client-id}/stores", store.getOwner().getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getClientStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList
        restStoreMockMvc.perform(get("/api/clients/{client-id}/stores", store.getOwner().getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").value(hasSize(1)))
                .andExpect(jsonPath("$.content.[*].number").value(hasItem(store.getId().intValue())))
                .andExpect(jsonPath("$.content.[*].url").value(hasItem(DEFAULT_STORE_URL.toString())))
                .andExpect(jsonPath("$.content.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.content.[*].desiredReviewsNumber").value(hasItem(DEFAULT_DESIRED_REVIEWS_NUMBER)))
                .andExpect(jsonPath("$.content.[*].created").value(store.getCreated().toString()));
    }

    @Test
    @Transactional
    public void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc.perform(get("/api/clients/{client-id}/stores/{id}",
                store.getOwner().getId(), store.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.number").value(store.getId().intValue()))
                .andExpect(jsonPath("$.url").value(DEFAULT_STORE_URL.toString()))
                .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
                .andExpect(jsonPath("$.desiredReviewsNumber").value(DEFAULT_DESIRED_REVIEWS_NUMBER))
                .andExpect(jsonPath("$.created").value(store.getCreated().toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        Store updatedStore = storeRepository.findOne(store.getId());
        updatedStore.setUrl(UPDATED_STORE_URL);
        updatedStore.setActive(UPDATED_ACTIVE);
        updatedStore.setDesiredReviewsNumber(UPDATED_DESIRED_REVIEWS_NUMBER);
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(updatedStore);

        restStoreMockMvc.perform(put("/api/clients/{client-id}/stores/{id}",
                store.getOwner().getId(), store.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
                .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getUrl()).isEqualTo(UPDATED_STORE_URL);
        assertThat(testStore.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testStore.getDesiredReviewsNumber()).isEqualTo(UPDATED_DESIRED_REVIEWS_NUMBER);
    }

    @Test
    @Transactional
    public void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Get the store
        restStoreMockMvc.perform(delete("/api/clients/{client-id}/stores/{id}",
                store.getOwner().getId(), store.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

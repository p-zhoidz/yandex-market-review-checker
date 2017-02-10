package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Poster;
import by.pzh.yandex.market.review.checker.repository.PosterRepository;
import by.pzh.yandex.market.review.checker.service.dto.PosterDTO;
import by.pzh.yandex.market.review.checker.service.impl.PosterService;
import by.pzh.yandex.market.review.checker.service.mappers.PosterMapper;
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
 * Test class for the PosterResource REST controller.
 *
 * @see PosterController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
public class PosterControllerSpringTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final Integer DEFAULT_VELOCITY = 1;
    private static final Integer UPDATED_VELOCITY = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Inject
    private PosterRepository posterRepository;

    @Inject
    private PosterService posterService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private ExceptionTranslator exceptionTranslator;

    @Inject
    private PosterMapper posterMapper;

    @Inject
    private EntityManager em;

    private MockMvc restPosterMockMvc;

    private Poster poster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PosterController posterResource = new PosterController(posterService, posterMapper);

        this.restPosterMockMvc = MockMvcBuilders.standaloneSetup(posterResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poster createEntity() {
        return Poster.builder()
                .email(DEFAULT_EMAIL)
                .name(DEFAULT_NAME)
                .rate(DEFAULT_RATE)
                .velocity(DEFAULT_VELOCITY)
                .active(DEFAULT_ACTIVE)
                .build();
    }

    @Before
    public void initTest() {
        poster = createEntity();
    }

    @Test
    @Transactional
    public void createPoster() throws Exception {
        int databaseSizeBeforeCreate = posterRepository.findAll().size();

        // Create the Poster
        PosterDTO posterDTO = posterMapper.posterToPosterDTO(poster);

        restPosterMockMvc.perform(post("/api/posters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(posterDTO)))
                .andExpect(status().isCreated());

        // Validate the Poster in the database
        List<Poster> posterList = posterRepository.findAll();
        assertThat(posterList).hasSize(databaseSizeBeforeCreate + 1);
        Poster testPoster = posterList.get(posterList.size() - 1);
        assertThat(testPoster.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPoster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPoster.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testPoster.getVelocity()).isEqualTo(DEFAULT_VELOCITY);
        assertThat(testPoster.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = posterRepository.findAll().size();
        // set the field null
        poster.setEmail(null);

        // Create the Poster, which fails.
        PosterDTO posterDTO = posterMapper.posterToPosterDTO(poster);

        restPosterMockMvc.perform(post("/api/posters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(posterDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Poster> posterList = posterRepository.findAll();
        assertThat(posterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = posterRepository.findAll().size();
        // set the field null
        poster.setName(null);

        // Create the Poster, which fails.
        PosterDTO posterDTO = posterMapper.posterToPosterDTO(poster);

        restPosterMockMvc.perform(post("/api/posters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(posterDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Poster> posterList = posterRepository.findAll();
        assertThat(posterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = posterRepository.findAll().size();
        // set the field null
        poster.setRate(null);

        // Create the Poster, which fails.
        PosterDTO posterDTO = posterMapper.posterToPosterDTO(poster);

        restPosterMockMvc.perform(post("/api/posters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(posterDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Poster> posterList = posterRepository.findAll();
        assertThat(posterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = posterRepository.findAll().size();
        // set the field null
        poster.setVelocity(null);

        // Create the Poster, which fails.
        PosterDTO posterDTO = posterMapper.posterToPosterDTO(poster);

        restPosterMockMvc.perform(post("/api/posters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(posterDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Poster> posterList = posterRepository.findAll();
        assertThat(posterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPosters() throws Exception {
        // Initialize the database
        posterRepository.saveAndFlush(poster);

        // Get all the posterList
        restPosterMockMvc.perform(get("/api/posters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").value(hasSize(1)))
                .andExpect(jsonPath("$.content.[*].number").value(hasItem(poster.getId().intValue())))
                .andExpect(jsonPath("$.content.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.content.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.content.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
                .andExpect(jsonPath("$.content.[*].velocity").value(hasItem(DEFAULT_VELOCITY)))
                .andExpect(jsonPath("$.content.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPoster() throws Exception {
        // Initialize the database
        posterRepository.saveAndFlush(poster);

        // Get the poster
        restPosterMockMvc.perform(get("/api/posters/{id}", poster.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$.number").value(poster.getId().intValue()))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
                .andExpect(jsonPath("$.velocity").value(DEFAULT_VELOCITY))
                .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPoster() throws Exception {
        // Get the poster
        restPosterMockMvc.perform(get("/api/posters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoster() throws Exception {
        // Initialize the database
        posterRepository.saveAndFlush(poster);
        int databaseSizeBeforeUpdate = posterRepository.findAll().size();

        // Update the poster
        Poster updatedPoster = posterRepository.findOne(poster.getId());
        updatedPoster.setEmail(UPDATED_EMAIL);
        updatedPoster.setName(UPDATED_NAME);
        updatedPoster.setRate(UPDATED_RATE);
        updatedPoster.setVelocity(UPDATED_VELOCITY);
        updatedPoster.setActive(UPDATED_ACTIVE);
        PosterDTO posterDTO = posterMapper.posterToPosterDTO(updatedPoster);

        restPosterMockMvc.perform(put("/api/posters/{id}", updatedPoster.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(posterDTO)))
                .andExpect(status().isOk());

        // Validate the Poster in the database
        List<Poster> posterList = posterRepository.findAll();
        assertThat(posterList).hasSize(databaseSizeBeforeUpdate);
        Poster testPoster = posterList.get(posterList.size() - 1);
        assertThat(testPoster.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPoster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPoster.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testPoster.getVelocity()).isEqualTo(UPDATED_VELOCITY);
        assertThat(testPoster.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void deletePoster() throws Exception {
        // Initialize the database
        posterRepository.saveAndFlush(poster);
        int databaseSizeBeforeDelete = posterRepository.findAll().size();

        // Get the poster
        restPosterMockMvc.perform(delete("/api/posters/{id}", poster.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Poster> posterList = posterRepository.findAll();
        assertThat(posterList).hasSize(databaseSizeBeforeDelete - 1);
    }


}

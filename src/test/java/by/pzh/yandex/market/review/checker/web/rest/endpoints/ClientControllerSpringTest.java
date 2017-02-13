package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.repository.ClientRepository;
import by.pzh.yandex.market.review.checker.service.dto.ClientDTO;
import by.pzh.yandex.market.review.checker.service.impl.ClientService;
import by.pzh.yandex.market.review.checker.service.mappers.ClientMapper;
import by.pzh.yandex.market.review.checker.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the CustomerResource REST controller.
 *
 * @see ClientController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
public class ClientControllerSpringTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_COMMENT = "Comment";
    private static final String UPDATED_COMMENT = "Updated Comment";

    @Inject
    private ExceptionTranslator controllerAdvice;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private ClientService clientService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private ClientMapper clientMapper;

    @Inject
    private EntityManager em;

    private MockMvc restClientMockMvc;

    private Client client;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientController customerResource = new ClientController(clientService, clientMapper);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
                .setControllerAdvice(controllerAdvice)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity() {
        return Client.builder()
                .email(DEFAULT_EMAIL)
                .active(DEFAULT_ACTIVE)
                .comment(DEFAULT_COMMENT)
                .build();
    }

    @Before
    public void initTest() {
        client = createEntity();
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        ClientDTO clientDTO = getClientDTO(client);
        // Create the Client

        restClientMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
                .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
                .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
                .andExpect(jsonPath("$.created").value(notNullValue()));

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testClient.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testClient.getCreated()).isNotNull();
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();

        ClientDTO clientDTO = getClientDTO(client);
        clientDTO.setEmail(null);

        restClientMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the customerList
        ResultActions perform = restClientMockMvc.perform(get("/api/clients?page=0"));
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").value(hasSize(1)))
                .andExpect(jsonPath("$.content.[*].id").value(hasItem(client.getId().intValue())))
                .andExpect(jsonPath("$.content.[*].email").value(hasItem(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.content.[*].active").value(hasItem(DEFAULT_ACTIVE)))
                .andExpect(jsonPath("$.content.[*].comment").value(hasItem(DEFAULT_COMMENT)))
                .andExpect(jsonPath("$.content.[*].created").value(client.getCreated().toString()))
                .andExpect(jsonPath("$.last").value("true"))
                .andExpect(jsonPath("$.totalPages").value("1"))
                .andExpect(jsonPath("$.totalElements").value("1"))
                .andExpect(jsonPath("$.sort").value(isEmptyOrNullString()))
                .andExpect(jsonPath("$.numberOfElements").value("1"))
                .andExpect(jsonPath("$.first").value("true"))
                .andExpect(jsonPath("$.size").value("10"))
                .andExpect(jsonPath("$.number").value("0"));


    }

    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
                .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
                .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
                .andExpect(jsonPath("$.created").value(client.getCreated().toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findOne(client.getId());
        updatedClient.setEmail(UPDATED_EMAIL);
        updatedClient.setActive(UPDATED_ACTIVE);
        updatedClient.setComment(UPDATED_COMMENT);


        ClientDTO updatedClientDTO = getClientDTO(updatedClient);

        restClientMockMvc.perform(put("/api/clients/{id}", client.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClientDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testClient.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testClient.getCreated()).isEqualTo(client.getCreated());
    }


    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Get the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    private ClientDTO getClientDTO(Client client) {
        return ClientDTO.builder()
                .active(client.getActive())
                .comment(client.getComment())
                .created(client.getCreated())
                .email(client.getEmail())
                .id(client.getId())
                .build();
    }
}

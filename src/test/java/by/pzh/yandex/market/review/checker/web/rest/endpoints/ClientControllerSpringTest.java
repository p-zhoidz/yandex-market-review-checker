package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.domain.Client;
import by.pzh.yandex.market.review.checker.repository.ClientRepository;
import by.pzh.yandex.market.review.checker.service.dto.ClientDTO;
import by.pzh.yandex.market.review.checker.service.impl.ClientService;
import by.pzh.yandex.market.review.checker.service.mappers.ClientMapper;
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
import static org.hamcrest.Matchers.hasSize;
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

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private ClientMapper clientMapper;

    @Inject
    private ClientService clientService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCustomerMockMvc;

    private Client client;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientController customerResource = new ClientController();
        ReflectionTestUtils.setField(customerResource, "clientService", clientService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = Client.builder()
                .email(DEFAULT_EMAIL)
                .active(DEFAULT_ACTIVE)
                .build();
        return client;
    }

    @Before
    public void initTest() {
        client = createEntity(em);
    }

    //FIXME
/*    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);

        restCustomerMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }*/

    //FIXME
/*    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        Client existingClient = new Client();
        existingClient.setId(1L);
        ClientDTO existingClientDTO = clientMapper.clientToClientDTO(existingClient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingClientDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }*/

//FIXME
/*    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setEmail(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);

        restCustomerMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }*/

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/clients?page=0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content.[*].id").isArray())
                .andExpect(jsonPath("$.content.[*].id").value(hasSize(1)))
                .andExpect(jsonPath("$.content.[*].id").value(hasItem(client.getId().intValue())))
                .andExpect(jsonPath("$.content.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.content.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.content.[0].links").isArray())
                .andExpect(jsonPath("$.content.[0].links").value(hasSize(1)))
                .andExpect(jsonPath("$.content.[0].links.[*].rel").value(hasItem("self")))
                .andExpect(jsonPath("$.content.[0].links.[*].href").value(hasItem("http://localhost/api/clients/1")));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restCustomerMockMvc.perform(get("/api/clients/{id}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(client.getId().intValue()))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the client
        restCustomerMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    //FIXME
/*    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findOne(client.getId());
        updatedClient.setEmail(UPDATED_EMAIL);
        updatedClient.setActive(UPDATED_ACTIVE);
        ClientDTO clientDTO = clientMapper.clientToClientDTO(updatedClient);

        restCustomerMockMvc.perform(put("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getActive()).isEqualTo(UPDATED_ACTIVE);
    }*/

//FIXME
/*    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerMockMvc.perform(put("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate + 1);
    }*/

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Get the client
        restCustomerMockMvc.perform(delete("/api/clients/{id}", client.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

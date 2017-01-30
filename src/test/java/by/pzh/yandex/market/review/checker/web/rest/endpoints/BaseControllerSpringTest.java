package by.pzh.yandex.market.review.checker.web.rest.endpoints;

import by.pzh.yandex.market.review.checker.ApplicationTestContext;
import by.pzh.yandex.market.review.checker.web.rest.assemblers.BaseResourceAssembler;
import by.pzh.yandex.market.review.checker.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author p.zhoidz.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTestContext.class)
public class BaseControllerSpringTest {

    @Inject
    private BaseResourceAssembler baseResourceAssembler;

    @Inject
    private ExceptionTranslator exceptionTranslator;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;


    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;


    private MockMvc restBaseControllerMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BaseController baseController = new BaseController(baseResourceAssembler);

        this.restBaseControllerMockMvc = MockMvcBuilders.standaloneSetup(baseController)
                .setControllerAdvice(exceptionTranslator)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    public void testBaseControllerPayload() throws Exception {
        final String expectedPayload = "{\"_links\":{\"clients\":\"http://localhost/api/clients\","
                + "\"posters\":\"http://localhost/api/posters\"}}";

        MockHttpServletResponse response = restBaseControllerMockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andReturn()
                .getResponse();

        String contentAsString = response.getContentAsString();

        assertEquals(expectedPayload, contentAsString);
    }
}
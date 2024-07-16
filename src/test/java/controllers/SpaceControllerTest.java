package controllers;

import dao.UserDao;
import dto.Space;
import in.controllers.SpaceController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import secret.JwtUtils;
import services.SpaceService;

import java.util.ArrayList;
import java.util.Optional;

import static org.apache.http.client.methods.RequestBuilder.get;
import static org.apache.http.client.methods.RequestBuilder.post;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class SpaceControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SpaceService mockService;
    @Mock
    UserDao userDao;
    JwtUtils jwtUtils = new JwtUtils(userDao);

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SpaceController(mockService, jwtUtils)).build();
    }

    @Test
    @DisplayName("тест создания места")
    public void testCreateSpace() throws Exception {
        when(mockService.createSpace("Комната-1")).thenReturn(new Space("Комната-1"));
        mockMvc.perform((RequestBuilder) post("/space").addParameter("name", "Комната-1").addHeader("Authorization", "Bearer " + jwtUtils.generateJwtToken("123")));
    }

    @Test
    @DisplayName("Тест получение мест")
    public void testGetSpace() {
        when(mockService.getAllSpaces()).thenReturn(new ArrayList<Space>(){{
            add(new Space("1"));
            add(new Space("2"));
            add(new Space("3"));
        }
        });
        try {
            mockMvc.perform((RequestBuilder) get("/space")).andExpect(status().isOk())
                    .andExpect((ResultMatcher) content().string(containsString(
                            "[{\"id\":null,\"name\":\"1\"}" +
                                    ",{\"id\":null,\"name\":\"2\"}," +
                                    "{\"id\":null,\"name\":\"3\"}]")));
        } catch (Exception e) {
            fail();
        }
    }

}

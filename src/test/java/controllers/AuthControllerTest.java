package controllers;

import dao.UserDao;
import dto.User;
import in.controllers.AuthController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import secret.JwtUtils;
import services.AuthorizationService;

import java.util.Optional;

import static org.apache.http.client.methods.RequestBuilder.post;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@WebAppConfiguration
public class AuthControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AuthorizationService mockService;
    @Mock
    UserDao userDao;
    JwtUtils jwtUtils = new JwtUtils(userDao);

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(mockService, jwtUtils)).build();
    }
    @Test
    @DisplayName("Тестирование входа")
    public void testLogin() {
        User testUser = new User("123","12345");
        when(userDao.getUserByUsername("123")).thenReturn(Optional.of(testUser));
        try {
            mockMvc.perform((RequestBuilder) post("/auth/login").addParameter("user","123").addParameter("password","12345"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Тестирование регистрации")
    public void testRegister() {
        User testUser = new User("123","12345");
        when(userDao.getUserByUsername("123")).thenReturn(Optional.of(testUser));
        try {
            mockMvc.perform((RequestBuilder) post("/auth/register").addParameter("user","123").addParameter("password","12345"));
        } catch (Exception e) {
            fail();
        }
    }
}

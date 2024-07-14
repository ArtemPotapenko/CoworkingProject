package servlets;

import dao.UserDao;
import in.servlets.LoginServlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

import static org.mockito.Mockito.when;

public class BookServletTest {

    BookServletTest bookServletTest = new BookServletTest();
    UserDao userDao = new UserDao();

    public LoginServletTest() throws IOException {
        when(httpServletRequestWrapper.getReader().readLine()).thenReturn("{username:\"test\", password:\"test\"}");
    }

    HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(null);
    HttpServletResponseWrapper httpServletResponseWrapper = new HttpServletResponseWrapper(null);
    @Test
    @DisplayName("авторизация пользователя")
    public void testLogin() throws IOException {
        loginServlet.doPost(httpServletRequestWrapper, httpServletResponseWrapper);
        Assertions.assertTrue(userDao.getUserByUsername("test").isPresent());
    }

}

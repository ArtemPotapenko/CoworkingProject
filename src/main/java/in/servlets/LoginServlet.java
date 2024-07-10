package in.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Massage;
import dto.UserRequest;
import exceptions.PasswordNotEqualsException;
import exceptions.UserNotFoundException;
import in.RequestReader;
import out.ResponseWriter;
import services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;

/**
 * Сервлет для входа
 */
@WebServlet("auth/login")
public class LoginServlet extends HttpServlet {
    private final AuthorizationService authorizationService = new AuthorizationService();
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       UserRequest userRequest = RequestReader.readRequest(req,UserRequest.class);
        try {
            req.getSession().setAttribute("user",authorizationService.login(userRequest.getUsername(),userRequest.getPassword()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserNotFoundException e) {
            ResponseWriter.write(new Massage("Пользователь не найден"),resp);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (PasswordNotEqualsException e) {
            ResponseWriter.write(new Massage("Пароль неверный"),resp);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}

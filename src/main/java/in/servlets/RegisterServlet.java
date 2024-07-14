package in.servlets;

import dto.Massage;
import dto.UserRequest;
import exceptions.PasswordNotEqualsException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;
import in.RequestReader;
import out.ResponseWriter;
import services.AuthorizationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для регистрации
 */
@WebServlet("auth/register")
public class RegisterServlet extends HttpServlet {
    private final AuthorizationService authorizationService = new AuthorizationService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserRequest userRequest = RequestReader.readRequest(req,UserRequest.class);
        try {
            req.getSession().setAttribute("user",authorizationService.register(userRequest.getUsername(), userRequest.getPassword()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserAlreadyExistException e) {
            ResponseWriter.write(new Massage("Пользователь уже существует"),resp);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}

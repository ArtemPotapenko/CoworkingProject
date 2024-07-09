package in.servlets;

import dto.SpaceRequest;
import in.RequestReader;
import out.ResponseWriter;
import services.SpaceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Обработка запросов на создание и вывод рабочих мест
 */
@WebServlet(value = "/spaces", name = "spaces")
public class SpaceServlet extends HttpServlet {
    private final SpaceService spaceService = new SpaceService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseWriter.write(spaceService.getAllSpaces(),resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        spaceService.createSpace(RequestReader.readRequest(req, SpaceRequest.class).getSpaceName());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}

package in.servlets;

import dto.Massage;
import dto.User;
import exceptions.BookingNotFoundException;
import out.ResponseWriter;
import services.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для работы с бронированием
 */
@WebServlet(urlPatterns = {"book/**"}, name = "book")
public class BookAndCancelServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] split = req.getRequestURI().split("/");
        long id = Long.parseLong(split[split.length - 1]);
        try {
            bookingService.cancelBooking((User) req.getSession().getAttribute("user"), id);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (BookingNotFoundException e) {
            ResponseWriter.write(new Massage("Бронированик не найдено"), resp);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null && req.getSession().getAttribute("user") instanceof User) {
            String[] split = req.getRequestURI().split("/");
            long id = Long.parseLong(split[split.length - 1]);
            try {
                bookingService.book((User) req.getSession().getAttribute("user"), id);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (BookingNotFoundException e) {
                ResponseWriter.write(new Massage("Бронированик не найдено"), resp);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}

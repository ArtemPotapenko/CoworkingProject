package in.servlets;

import dto.BookRequest;
import dto.Massage;
import dto.User;
import exceptions.BookingNotFoundException;
import exceptions.SpaceNotFoundException;
import in.RequestReader;
import out.ResponseWriter;
import services.BookingService;
import services.SpaceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для работы с бронированием
 */
@WebServlet(value = "/book", name = "bookings")
public class BookingsServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService();
    private final SpaceService spaceService = new SpaceService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseWriter.write(bookingService.getBookingsByUser((User) req.getSession().getAttribute("user")),resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookRequest bookRequest = RequestReader.readRequest(req, BookRequest.class);
        try {
            bookingService.addBooking(spaceService.getSpace(bookRequest.getSpaceId()),bookRequest.getStartDate(), bookRequest.getEndDate());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SpaceNotFoundException e) {
            ResponseWriter.write(new Massage("Место не найдено"),resp);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

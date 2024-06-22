package commands;

import dto.User;
import exceptions.ExitException;
import out.UserWriter;
import services.BookingService;
/**
 * вывести свои бронирования
 * @version 1.0
 * @author potap
 */
public class PrintBookingUserCommand implements Command{
    private BookingService bookingService = new BookingService();
    @Override
    public void execute(User user) throws ExitException {
        UserWriter.print("Список:");
        bookingService.getBookingsByUser(user).forEach(UserWriter::print);
    }

    @Override
    public String description() {
        return "вывести свои бронирования";
    }
}

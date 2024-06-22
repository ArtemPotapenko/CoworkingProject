package commands;

import dto.User;
import exceptions.ExitException;
import in.UserReader;
import out.UserWriter;
import services.BookingService;
/**
 * вывести свободные бронирования по дате
 * @version 1.0
 * @author potap
 */
public class PrintBookingDateCommand implements Command{
    private final BookingService bookingService = new BookingService();
    @Override
    public void execute(User user) throws ExitException {
        System.out.println("Введите дату бронирования");
        bookingService.getFreeBookingByDate(UserReader.getInstance().readDay()).forEach(UserWriter::print);
    }

    @Override
    public String description() {
        return "вывести свободные бронирования по дате";
    }
}

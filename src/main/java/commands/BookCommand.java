package commands;

import dto.User;
import exceptions.BookingNotFoundException;
import exceptions.ExitException;
import in.UserReader;
import out.UserWriter;
import services.BookingService;
/**
 * бронирование рабочего места
 * @version 1.0
 * @author potap
 */
public class BookCommand implements Command{
    private final BookingService bookingService = new BookingService();
    @Override
    public void execute(User user) throws ExitException {
        UserWriter.print("Введите id брони:");
        try {
            bookingService.book(user, UserReader.getInstance().readLong());
        } catch (BookingNotFoundException e) {
            UserWriter.print("Бронирование не найдено");
        }
    }

    @Override
    public String description() {
        return "бронирование рабочего места";
    }
}

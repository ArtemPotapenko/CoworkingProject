package commands;

import dto.User;
import exceptions.BookingNotFoundException;
import exceptions.ExitException;
import in.UserReader;
import out.UserWriter;
import services.BookingService;
/**
 * отменить бронь
 * @version 1.0
 * @author potap
 */
public class CancelCommand implements Command{
    private final BookingService bookingService = new BookingService();
    @Override
    public void execute(User user) throws ExitException {
        UserWriter.print("Введите id брони");
        try {
            bookingService.cancelBooking(UserReader.getInstance().readLong());
        } catch (BookingNotFoundException e) {
            UserWriter.print("Бронь не найдена");
        }
    }

    @Override
    public String description() {
        return "отменить бронь";
    }
}

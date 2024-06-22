package commands;

import dto.Booking;
import dto.Space;
import dto.User;
import exceptions.ExitException;
import exceptions.SpaceNotFoundException;
import in.UserReader;
import out.UserWriter;
import services.BookingService;
import services.SpaceService;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;

/**
 * добавить новый слот
 * @version 1.0
 * @author potap
 */
public class AddBookingCommand implements Command {
    private final BookingService bookingService = new BookingService();
    private final SpaceService spaceService = new SpaceService();


    @Override
    public void execute(User user) throws ExitException {
        UserWriter.print("Введите id рабочего места");
        Long id = UserReader.getInstance().readLong();
        try {
            Space space = spaceService.getSpace(id);
            UserWriter.print("Введите дату начала бронирования:");
            Date startDate = UserReader.getInstance().readDate();
            UserWriter.print("Введите дату конца бронирования:");
            Date endDate = UserReader.getInstance().readDate();
            bookingService.addBooking(space,startDate,endDate);
        } catch (SpaceNotFoundException e) {
            UserWriter.print("Рабочее место не найдено");
        }
    }

    @Override
    public String description() {
        return "добавить новый слот";
    }
}

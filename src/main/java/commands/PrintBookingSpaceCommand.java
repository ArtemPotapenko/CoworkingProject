package commands;

import dto.Space;
import dto.User;
import exceptions.ExitException;
import exceptions.SpaceNotFoundException;
import in.UserReader;
import out.UserWriter;
import services.BookingService;
import services.SpaceService;
/**
 * вывести все доступные бронирования данного помещения
 * @version 1.0
 * @author potap
 */
public class PrintBookingSpaceCommand implements Command{
    private final BookingService bookingService = new BookingService();
    private final SpaceService spaceService = new SpaceService();
    @Override
    public void execute(User user) throws ExitException {
        UserWriter.print("Введите id рабочего места");
        try {
            Space space =  spaceService.getSpace(UserReader.getInstance().readLong());
            UserWriter.print("Список:");
            bookingService.getFreeBookings().stream().filter(x -> x.getSpace().equals(space)).forEach(UserWriter::print);
        } catch (SpaceNotFoundException e) {
            UserWriter.print("Рабочее место не найдено");
        }
    }

    @Override
    public String description() {
        return "вывести все доступные бронирования данного помещения";
    }
}

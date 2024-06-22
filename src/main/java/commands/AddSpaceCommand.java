package commands;

import dao.SpaceDao;
import dto.Booking;
import dto.User;
import exceptions.ExitException;
import in.UserReader;
import out.UserWriter;
import services.SpaceService;
/**
 * Добавить новое рабочее место
 * @version 1.0
 * @author potap
 */
public class AddSpaceCommand implements Command{
    private final SpaceService spaceService = new SpaceService();
    @Override
    public void execute(User user) throws ExitException {
        UserWriter.print("Введите имя нового рабочего места");
        spaceService.createSpace(UserReader.getInstance().readString());
    }

    @Override
    public String description() {
        return "Добавить новое рабочее место";
    }
}

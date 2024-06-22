package commands;

import dto.User;
import exceptions.ExitException;
import out.UserWriter;
import services.SpaceService;
/**
 * вывести все рабочие пространства
 * @version 1.0
 * @author potap
 */
public class PrintSpacesCommand implements Command{
    private final SpaceService spaceService = new SpaceService();
    @Override
    public void execute(User user) throws ExitException {
        UserWriter.print("Список:");
        spaceService.getAllSpaces().forEach(UserWriter::print);

    }

    @Override
    public String description() {
        return "вывести все рабочие пространства";
    }
}

package commands;

import dto.User;
import exceptions.ExitException;
/**
 * завершить выполнение команд
 * @version 1.0
 * @author potap
 */
public class ExitCommand implements Command{

    @Override
    public void execute(User user) throws ExitException {
          throw new ExitException();
    }

    @Override
    public String description() {
        return "завершить выполнение команд";
    }
}

package commands;

import dto.User;
import exceptions.ExitException;

/**
 * Команда
 * @author potap
 */
public interface Command {
    void execute(User user) throws ExitException;

    String description();
}

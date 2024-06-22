package services;

import commands.*;
import dto.User;
import exceptions.ExitException;
import out.UserWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для запуска всех команд
 * @author potap
 * @version 1.0
 */
public class ExecuteService {
    List<Command> commands = new ArrayList<>();


    /**
     * Команда для выода списка команд
     */
    class HelpCommand implements Command {
        private final UserWriter userWriter = new UserWriter();
        @Override
        public void execute(User user) {
            System.out.println();
            for (int i = 0; i < commands.size(); i++) {
                userWriter.print(i + "-" + commands.get(i).description());

            }
        }

        @Override
        public String description() {
            return "Вывести список команд";
        }
    }

    public ExecuteService() {
        commands.add(new HelpCommand());
        commands.add(new AddSpaceCommand());
        commands.add(new AddBookingCommand());
        commands.add(new BookCommand());
        commands.add(new CancelCommand());
        commands.add(new PrintBookingDateCommand());
        commands.add(new PrintBookingSpaceCommand());
        commands.add(new PrintBookingUserCommand());
        commands.add(new PrintSpacesCommand());
        commands.add(new ExitCommand());
    }

    /**
     * Режим выполнения команд
     *
     * @param user Пользователь
     * @param commandNumber Номер команды
     * @throws ExitException Выход из режима выполнения команд
     */
    public void execute(User user, int commandNumber) throws ExitException {
        System.out.println("Номер команды:");
        commands.get(commandNumber).execute(user);
        UserWriter.print("");
    }
}

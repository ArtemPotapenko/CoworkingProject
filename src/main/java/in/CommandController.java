package in;

import dto.User;
import exceptions.ExitException;
import services.ExecuteService;

public class CommandController {
    private final ExecuteService executeService= new ExecuteService();
    private final UserReader userReader = UserReader.getInstance();
    public void execute(User user){
        System.out.println("Выберете команду");
        try {
            executeService.execute(user,0);
        } catch (ExitException e) {
            throw new RuntimeException(e);
        }
        while (true){
            try {
                executeService.execute(user, userReader.readInt());
            } catch (ExitException e) {
                break;
            }
        }
    }
}

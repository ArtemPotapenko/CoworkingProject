import exceptions.ExitException;
import in.AuthController;
import in.CommandController;

public class Main {
    public static void main(String[] args) {
        AuthController authController = new AuthController();
        CommandController commandController = new CommandController();
        while (true) {
            try {
                commandController.execute(authController.auth());
            } catch (ExitException ignored) {

            }
        }

    }
}

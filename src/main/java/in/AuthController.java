package in;

import dto.User;
import exceptions.ExitException;
import exceptions.PasswordNotEqualsException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;
import services.AuthorizationService;

import java.util.InputMismatchException;

public class AuthController {
    private final UserReader userReader = UserReader.getInstance();
    private final AuthorizationService authorizationService = new AuthorizationService();

    public User auth() throws ExitException {
        System.out.println("Введите:");
        System.out.println("1 - для входа");
        System.out.println("2 - для регистрации");
        System.out.println("иначе - выход из приложения");
        switch (userReader.readInt()) {
            case 1:
                try {
                    return authorizationService.login(userReader.readString(), userReader.readString());
                } catch (UserNotFoundException | PasswordNotEqualsException e) {
                    System.out.println("Неверный логин или пароль");
                    throw new ExitException();
                }
            case 2:
                try {
                    return authorizationService.register(userReader.readString(), userReader.readString());
                } catch (UserAlreadyExistException e) {
                    System.out.println("Такой пользователь уже существует");
                    throw new ExitException();
                }
            default:
                System.exit(0);
                return null;
        }
    }
}

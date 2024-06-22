package services;

import dao.UserDao;
import dto.User;
import exceptions.PasswordNotEqualsException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;

import java.util.Base64;
/**
 * Сервис для авторизации и регистрации
 * @author potap
 * @version 1.0
 *
 */
public class AuthorizationService {
    private final UserDao userDao = new UserDao();

    /**
     * @param username Имя пользователя
     * @param password Пароль
     * @return Зарегестрированный пользователь
     * @throws UserAlreadyExistException ошибка о том, что пользователь уже существует.
     *                                   <p>
     *                                   Регистрация пользователя
     */
    public User register(String username, String password) throws UserAlreadyExistException {
        return userDao.createUser(new User(username, Base64.getEncoder().encodeToString(password.getBytes())));
    }

    /**
     *
     * @param password введенный пароль
     * @param encodePassword закодированный пароль
     * @return результат проверки совпадения
     */
    private boolean passwordEquals(String password, String encodePassword) {
        return Base64.getEncoder().encodeToString(password.getBytes()).equals(encodePassword);
    }

    /**
     *
     * @param username Имя пользователя
     * @param password Пароль
     * @return Пользователь
     * @throws UserNotFoundException Пользователь не найден
     * @throws PasswordNotEqualsException Пароль не совпал
     *
     * Авторизация
     */
    public User login(String username, String password) throws UserNotFoundException, PasswordNotEqualsException {
        User user = userDao.getUserByUsername(username).orElseThrow(UserNotFoundException::new);
        if (passwordEquals(password, user.getEncodedPassword())) {
            return user;
        } else {
            throw new PasswordNotEqualsException();
        }
    }
}

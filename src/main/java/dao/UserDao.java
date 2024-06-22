package dao;

import dto.User;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CRUD для User
 * @author potap
 * @version 1.0
 */
public class UserDao {
    private final static Map<Long, User> users = new HashMap<>();
    private final static Map<String, Long> index = new HashMap<>();
    private long maxId = 0;

    /**
     * Сохранение пользователя
     * @param user данные нового пользователя
     * @return Пользователь с новым id
     * @throws UserAlreadyExistException пользователь уже существует
     *
     *
     */

    public User createUser(User user) throws UserAlreadyExistException {
        if (index.containsKey(user.getUsername())) {
            throw new UserAlreadyExistException();
        }
        index.put(user.getUsername(), maxId + 1);
        user.setId(maxId + 1);
        users.put(maxId + 1, user);
        maxId++;
        return user;
    }

    /**
     * Получить пользователя по id
     * @param id id пользователя
     * @return Найденный пользователь
     *
     */
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    /**
     * Вывод всех пользователей
     */
    public List<User> getUsers() {
        return users.values().stream().toList();
    }

    /**
     * Удаление пользователя
     * @param id id пользователя
     * @return успешное/неуспешное удаление
     *
     */
    public boolean deleteUser(Long id) {
        if (!users.containsKey(id)) {
            return false;
        } else {
            users.remove(id);
            return true;
        }
    }

    /**
     *  Получение пользователя по имени.
     * @param username имя пользователя
     * @return пользователь
     *
     */
    public Optional<User> getUserByUsername(String username) {
        if (index.containsKey(username)) {
            return Optional.ofNullable(users.get(index.get(username)));
        }
        return Optional.empty();
    }

    /**
     * Обновление пользователя
     * @param id Id
     * @param user Пользователь с обновленными данными
     * @return Обновленный пользователь из бд
     */
    public User updateUser(Long id, User user) throws UserNotFoundException {
        if (users.containsKey(id)) {
            users.put(id, user);
            user.setId(id);
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }
}

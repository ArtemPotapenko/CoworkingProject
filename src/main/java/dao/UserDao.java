package dao;

import dto.User;
import exceptions.JDBCException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;

import java.sql.*;
import java.util.*;

/**
 * CRUD для User
 *
 * @author potap
 * @version 1.0
 */
public class UserDao {
    private final Connection connection = new JBDCConnection().getConnection();

    /**
     * Сохранение пользователя
     *
     * @param user данные нового пользователя
     * @return Пользователь с новым id
     * @throws UserAlreadyExistException пользователь уже существует
     */

    public User createUser(User user) throws UserAlreadyExistException {
        if (getUserByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into users VALUES (nextval(user_seq),?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2, user.getEncodedPassword());
            preparedStatement.execute();
            user.setId(preparedStatement.getGeneratedKeys().getLong(1));
        } catch (
                SQLException e) {
            throw new JDBCException("Ошибка создания пользователя");
        }

        return user;
    }
    private User getUserByResultSet(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("username"), resultSet.getString("password"));
        user.setId(resultSet.getLong("id"));
        return user;
    }
    /**
     * Получить пользователя по id
     *
     * @param id id пользователя
     * @return Найденный пользователь
     */
    public Optional<User> getUserById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE  id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserByResultSet(resultSet));
            }
            else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new JDBCException("Ошибка получения пользователя");
        }

    }

    /**
     * Вывод всех пользователей
     */
    public List<User> getUsers() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(getUserByResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление пользователя
     *
     * @param id id пользователя
     * @return успешное/неуспешное удаление
     */
    public boolean deleteUser(Long id) {
        if (getUserById(id).isEmpty()){
            return false;
        }
        try {
            connection.createStatement().execute("DELETE FROM users WHERE id = " + id);
        } catch (SQLException e) {
            throw new JDBCException("Ошибка удаления пользователя");
        }

        return true;
    }

    /**
     * Получение пользователя по имени.
     *
     * @param username имя пользователя
     * @return пользователь
     */
    public Optional<User> getUserByUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE  ussername = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserByResultSet(resultSet));
            }
            else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new JDBCException("Ошибка получения пользователя");
        }


    }
}

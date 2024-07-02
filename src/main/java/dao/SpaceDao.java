package dao;

import dto.Space;
import dto.User;
import exceptions.JDBCException;
import exceptions.UserNotFoundException;

import java.sql.*;
import java.util.*;

/**
 * CRUD для Space
 *
 * @author potap
 * @version 1.0
 */
public class SpaceDao {
    private final Connection connection = new JBDCConnection().getConnection();

    /**
     * @param space рабочее место
     * @return рабочее место с новым id
     * <p>
     * Добавить рабочее место
     */
    public Space createSpace(Space space) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into users VALUES (nextval(space_seq),?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, space.getName());
            preparedStatement.execute();
            space.setId(preparedStatement.getGeneratedKeys().getLong(1));
        } catch (
                SQLException e) {
            throw new JDBCException("Ощибка создания бронирования");
        }
        return space;

    }

    /**
     * @param id
     * @return найденное место
     * <p>
     * Найти место по id
     */
    public Optional<Space> getSpaceById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM spaces WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Space space = new Space(resultSet.getString("name"));
                space.setId(resultSet.getLong("id"));
                return Optional.of(space);
            }else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new JDBCException("Ошибка получения места");
        }
    }

    /**
     * @return Все места
     */
    public List<Space> getAllSpaces() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM spaces");
            List<Space> spaces = new ArrayList<>();
            while (resultSet.next()) {
                Space space = new Space(resultSet.getString("name"));
                space.setId(resultSet.getLong("id"));
                spaces.add(space);
            }
            return spaces;
        } catch (SQLException e) {
            throw new JDBCException("Ошибка получения мест");
        }
    }

    /**
     * @param id Id
     * @return корректность удаления
     * <p>
     * Удалить место
     */
    public boolean deleteSpace(Long id) {
        if (getSpaceById(id).isEmpty()){
            return false;
        }
        try {
            connection.createStatement().execute("DELETE FROM spaces WHERE id = " + id);
        } catch (SQLException e) {
            throw new JDBCException("Ошибка удаления пользователя");
        }

        return true;

    }


}

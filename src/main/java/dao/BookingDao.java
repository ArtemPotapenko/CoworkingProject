package dao;

import dto.Booking;
import dto.Space;
import dto.User;
import exceptions.JDBCException;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;


/**
 * CRUD для бронирований
 *
 * @author potap
 * @version 1.0
 */
public class BookingDao {
    private final SpaceDao spaceDao = new SpaceDao();
    private final UserDao userDao = new UserDao();
    private final Connection connection = new JBDCConnection().getConnection();

    /**
     * Создание бронирования
     *
     * @param booking бронирование
     */
    public Booking createBooking(Booking booking) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into bookings(start_date, end_date, free, space_id, user_id) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, new java.sql.Date(booking.getStartTime().getTime()));
            preparedStatement.setDate(2, new java.sql.Date(booking.getEndTime().getTime()));
            preparedStatement.setBoolean(3, booking.isFree());
            preparedStatement.setLong(4, booking.getSpace().getId());
            preparedStatement.setLong(5, booking.getUser() == null ? 0 : booking.getUser().getId());
            preparedStatement.execute();
            preparedStatement.getGeneratedKeys().next();
            booking.setId(preparedStatement.getGeneratedKeys().getLong(1));
        } catch (SQLException e) {
            throw new JDBCException("Ощибка создания бронирования");
        }
        return booking;
    }

    private Booking getBookingByResultSet(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking(resultSet.getTime("start_time"), resultSet.getTime("start_time"), spaceDao.getSpaceById(resultSet.getLong("space_id")).orElse(null));
        booking.setFree(resultSet.getBoolean("free"));
        booking.setId(resultSet.getLong("id"));
        booking.setUser(userDao.getUserById(resultSet.getLong("user_id")).orElse(null));
        return booking;
    }

    /**
     * Получить бронирование по id
     *
     * @param id id бронирования
     */
    public Optional<Booking> getBookingById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM  Bookings where id = (?)");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookingByResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new JDBCException("Ошибка получения бронирования");
        }

    }

    /**
     * Все бронирования
     *
     * @return Список бронирований
     */
    public List<Booking> getBookings() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Bookings");
            List<Booking> bookings = new ArrayList<>();
            while (resultSet.next()) {
                bookings.add(getBookingByResultSet(resultSet));
            }
            return bookings;
        } catch (SQLException e) {
            throw new JDBCException("Ошибка получения бронирования");
        }
    }

    /**
     * Получить все свободные/несвободные бронирования
     *
     * @param free доступность
     * @return Список бронирований
     */
    public List<Booking> getBookingsByFree(boolean free) {
        return getBookings().stream().filter(x -> x.isFree() == free).toList();
    }

    /**
     * Вывести все бронирования пользователя
     *
     * @param user Пользователь
     * @return Список бронирований
     */
    public List<Booking> getBookingsByUser(User user) {
        return getBookings().stream().filter(x -> !x.isFree() && x.getUser().equals(user)).toList();
    }

    private boolean equalsDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    /**
     * Вывести все бронирования по дате и доступности
     *
     * @param date Дата
     * @return Список бронирований
     */
    public List<Booking> getBookingByDate(Date date, boolean free) {
        return getBookings().stream().filter(x -> equalsDate(x.getStartTime(), date) && free == x.isFree()).toList();
    }

    /**
     * Найти бронирование по дате и месту
     *
     * @param space Место
     * @param date  Дата
     * @return Нужное бронирование
     */
    public Optional<Booking> getBookingBySpaceAndDate(Space space, Date date) {
        return getBookings().stream().filter(x -> equalsDate(x.getStartTime(), date) && space == x.getSpace()).findFirst();
    }

    /**
     * Удалить бронирование
     *
     * @param id
     * @return
     */
    public boolean deleteBooking(Long id) {
        try {
            if (getBookingById(id).isEmpty()) {
                return false;
            }
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM bookings WHERE id = " + id);
            return true;
        } catch (SQLException e) {
            throw new JDBCException("Ошибка удаления бронирования");
        }
    }
}

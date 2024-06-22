package dao;

import dto.Booking;
import dto.Space;
import dto.User;

import java.time.LocalDate;
import java.util.*;

/**
 * CRUD для бронирований
 *
 * @author potap
 * @version 1.0
 */
public class BookingDao {
    private static Map<Long, Booking> map = new HashMap<>();
    private static long maxId = 0;

    /**
     * Создание бронирования
     *
     * @param booking бронирование
     */
    public Booking createBooking(Booking booking) {
        map.put(++maxId, booking);
        booking.setId(maxId);
        return booking;
    }

    /**
     * Получить бронирование по id
     *
     * @param id id бронирования
     */
    public Optional<Booking> getBookingById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    /**
     * Все бронирования
     *
     * @return Список бронирований
     */
    public List<Booking> getBookings() {
        return map.values().stream().toList();
    }

    /**
     * Получить все свободные/несвободные бронирования
     *
     * @param free доступность
     * @return Список бронирований
     */
    public List<Booking> getBookingsByFree(boolean free) {
        return map.values().stream().filter(x -> x.isFree() == free).toList();
    }

    /**
     * Вывести все бронирования пользователя
     *
     * @param user Пользователь
     * @return Список бронирований
     */
    public List<Booking> getBookingsByUser(User user) {
        return map.values().stream().filter(x -> !x.isFree() && x.getUser().equals(user)).toList();
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
        return map.values().stream().filter(x -> equalsDate(x.getStartTime(), date) && free == x.isFree()).toList();
    }

    /**
     * Найти бронирование по дате и месту
     *
     * @param space Место
     * @param date Дата
     * @return Нужное бронирование
     */
    public Optional<Booking> getBookingBySpaceAndDate(Space space, Date date){
        return map.values().stream().filter(x -> equalsDate(x.getStartTime(), date) && space == x.getSpace()).findFirst();
    }

    /**
     * Удалить бронирование
     * @param id
     * @return
     */
    public boolean deleteBooking(Long id){
        if (map.containsKey(id)){
            map.remove(id);
            return true;
        }else {
            return false;
        }
    }
}

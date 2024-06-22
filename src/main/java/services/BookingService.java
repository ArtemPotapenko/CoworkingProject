package services;

import dao.BookingDao;
import dto.Booking;
import dto.Space;
import dto.User;
import exceptions.BookingNotFoundException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для бронирований
 */
public class BookingService {
    private final BookingDao bookingDao = new BookingDao();

    /**
     * Бронирование
     * @param user
     * @param space
     * @param date
     * @throws BookingNotFoundException
     */
    public void book(User user, Space space, Date date) throws BookingNotFoundException {
        bookingDao.getBookingBySpaceAndDate(space, date).orElseThrow(BookingNotFoundException::new).reserve(user);
    }

    /**
     * Бронирование
     * @param user
     * @param id
     * @throws BookingNotFoundException
     */
    public void book(User user, Long id) throws BookingNotFoundException {
        bookingDao.getBookingById(id).orElseThrow(BookingNotFoundException::new).reserve(user);
    }
    public Booking addBooking(Space space, Date startDate, Date endDate){
        Booking booking = new Booking(startDate, endDate, space);
        space.addSlot(booking);
        return bookingDao.createBooking(booking);
    }
    public void removeBooking(Long id) throws BookingNotFoundException {
        if (!bookingDao.deleteBooking(id)){
            throw new BookingNotFoundException();
        }
    }

    /**
     * Получить свободные слоты
     * @return Доступные слоты
     */
    public List<Booking> getFreeBookings() {
        return bookingDao.getBookingsByFree(true);
    }

    /**
     * Вывести все бронирования пользователя
     *
     * @param user Пользователь
     * @return Список бронирований
     */
    public List<Booking> getBookingsByUser(User user) {
        return bookingDao.getBookingsByUser(user);
    }
    public void cancelBooking(Long id) throws BookingNotFoundException {
        bookingDao.getBookingById(id).orElseThrow(BookingNotFoundException::new).cancel();
    }


    /**
     * Вывести все доступные бронирования по дате
     *
     * @param date Дата
     * @return Список бронирований
     */
    public List<Booking> getFreeBookingByDate(Date date) {
        return bookingDao.getBookingByDate(date, true);
    }

    /**
     * Найти бронирование по дате и месту
     *
     * @param space Место
     * @param date Дата
     * @return Нужное бронирование
     */
    public Booking getBookingBySpaceAndDate(Space space, Date date) throws BookingNotFoundException {
        return bookingDao.getBookingBySpaceAndDate(space, date).orElseThrow(BookingNotFoundException::new);
    }

}

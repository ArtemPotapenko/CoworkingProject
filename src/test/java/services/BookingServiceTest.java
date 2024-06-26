package services;

import dto.User;
import exceptions.BookingNotFoundException;
import exceptions.SpaceNotFoundException;
import exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class BookingServiceTest {
    static SpaceService spaceService = new SpaceService();
    static AuthorizationService userService = new AuthorizationService();
    static BookingService bookingService = new BookingService();
    static User user;
    @BeforeAll
    public static void init() throws SpaceNotFoundException, UserAlreadyExistException, BookingNotFoundException {
        spaceService.createSpace("Кабинет - 1");
        bookingService.addBooking(spaceService.getSpace(1L),new Date(1000), new Date(2000000) );
        user = userService.register("NewUser","123");
        bookingService.book(user, 1L);
    }

    @Test
    void cancelBook() throws BookingNotFoundException, SpaceNotFoundException, UserAlreadyExistException {
        bookingService.cancelBooking(1L);
        Assertions.assertEquals(bookingService.getFreeBookings().size(), 1);
        bookingService.removeBooking(1L);
        Assertions.assertEquals(bookingService.getFreeBookings().size(), 0);
        Assertions.assertThrows(BookingNotFoundException.class,() -> bookingService.book(null, 1L));
        bookingService.addBooking(spaceService.getSpace(1L),new Date(1000), new Date(2000000) );
        bookingService.book(user, 2L);
    }
    @Test
    void bookTest() throws SpaceNotFoundException, UserAlreadyExistException, BookingNotFoundException {
        Assertions.assertTrue(bookingService.getFreeBookings().isEmpty());
        Assertions.assertEquals(bookingService.getBookingsByUser(user).get(0).getSpace().getName(),"Кабинет - 1");
    }

}

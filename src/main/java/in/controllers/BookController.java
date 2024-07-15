package in.controllers;

import dto.BookRequest;
import dto.Massage;
import dto.User;
import exceptions.BookingNotFoundException;
import exceptions.SpaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import secret.JwtUtils;
import services.BookingService;
import services.SpaceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Контролер для работы с бронированием
 */
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookingService bookingService;
    private final JwtUtils jwtUtils;
    private final SpaceService spaceService;

    /**
     * Бронирование помещения
     * @param id
     * @param request
     * @return
     */
    @PatchMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable long id, HttpServletRequest request) {
        if (jwtUtils.getUserFromJwtToken(request).isEmpty()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
        User user = jwtUtils.getUserFromJwtToken(request).get();
        try {
            bookingService.book(user, id);
            return ResponseEntity.ok().build();
        } catch (BookingNotFoundException e) {
            return ResponseEntity.badRequest().body(new Massage("Бронирование не найденно"));
        }
    }

    /**
     * Отмена бронирования
     * @param id
     * @param request
     * @return
     */
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id, HttpServletRequest request) {
        if (jwtUtils.getUserFromJwtToken(request).isEmpty()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
        User user = jwtUtils.getUserFromJwtToken(request).get();
        try {
            bookingService.cancelBooking(user, id);
            return ResponseEntity.ok().build();
        } catch (BookingNotFoundException e) {
            return ResponseEntity.badRequest().body(new Massage("Бронирование не найдено"));
        }
    }

    @GetMapping("/book/free")
    public ResponseEntity<?> getFreeBooks(HttpServletRequest request) {
        if (jwtUtils.getUserFromJwtToken(request).isEmpty()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
        return ResponseEntity.ok(bookingService.getFreeBookings());
    }

    @GetMapping("/book")
    public ResponseEntity<?> getUserBooks(HttpServletRequest request) {
        if (jwtUtils.getUserFromJwtToken(request).isEmpty()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
        User user = jwtUtils.getUserFromJwtToken(request).get();
        return ResponseEntity.ok(bookingService.getBookingsByUser(user));
    }

    @PostMapping("/book")
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest, HttpServletRequest request) {
        if (jwtUtils.getUserFromJwtToken(request).isEmpty()) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }
        User user = jwtUtils.getUserFromJwtToken(request).get();
        try {
            bookingService.addBooking(spaceService.getSpace(bookRequest.getSpaceId()),bookRequest.getStartDate(), bookRequest.getEndDate());
            return ResponseEntity.ok().build();
        } catch (SpaceNotFoundException e) {
            return ResponseEntity.badRequest().body(new Massage("Место не найдено."));
        }
    }

}

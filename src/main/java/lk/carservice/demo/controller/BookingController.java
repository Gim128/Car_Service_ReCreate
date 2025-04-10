package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.BookingDTO;
import lk.carservice.demo.dto.BookingResponseDTO;
import lk.carservice.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        BookingResponseDTO createdBooking = bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer carId,
            @RequestParam(required = false) String status) {

        if (userId != null) {
            return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
        } else if (carId != null) {
            return ResponseEntity.ok(bookingService.getBookingsByCar(carId));
        } else if (status != null) {
            return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
        }
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> updateBooking(
            @PathVariable Integer bookingId,
            @Valid @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, bookingDTO));
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Integer bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(bookingService.confirmBooking(bookingId));
    }

    @PostMapping("/{bookingId}/complete")
    public ResponseEntity<BookingResponseDTO> completeBooking(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(bookingService.completeBooking(bookingId));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<BookingResponseDTO>> getUpcomingBookings() {
        return ResponseEntity.ok(bookingService.getUpcomingBookings());
    }
}

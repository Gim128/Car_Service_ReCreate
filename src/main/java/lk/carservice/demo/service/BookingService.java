package lk.carservice.demo.service;

import lk.carservice.demo.dto.BookingDTO;
import lk.carservice.demo.dto.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingDTO bookingDTO);
    BookingResponseDTO getBookingById(Integer bookingId);
    List<BookingResponseDTO> getAllBookings();
    BookingResponseDTO updateBooking(Integer bookingId, BookingDTO bookingDTO);
    void cancelBooking(Integer bookingId);
    BookingResponseDTO confirmBooking(Integer bookingId);
    BookingResponseDTO completeBooking(Integer bookingId);
    List<BookingResponseDTO> getBookingsByUser(Integer userId);
    List<BookingResponseDTO> getBookingsByCar(Integer carId);
    List<BookingResponseDTO> getBookingsByStatus(String status);
    List<BookingResponseDTO> getUpcomingBookings();
}

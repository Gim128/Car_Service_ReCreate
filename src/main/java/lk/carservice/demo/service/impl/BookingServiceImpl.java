package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.BookingDTO;
import lk.carservice.demo.dto.BookingResponseDTO;
import lk.carservice.demo.entity.Booking;
import lk.carservice.demo.exceptions.BookingConflictException;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.BookingRepository;
import lk.carservice.demo.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingDTO bookingDTO) {
        // Check for overlapping bookings for the same card
        List<Booking> existingBookings = bookingRepository.findByCarIdAndBookingDateBetween(
                bookingDTO.getCarId(),
                bookingDTO.getBookingDate().minusHours(2),
                bookingDTO.getBookingDate().plusHours(2)
        );

        if (!existingBookings.isEmpty()) {
            throw new BookingConflictException("There is already a booking for this vehicle within 2 hours of the requested time");
        }

        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        booking.setStatus("PENDING");
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingResponseDTO.class);
    }

    @Override
    public BookingResponseDTO getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        return modelMapper.map(booking, BookingResponseDTO.class);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO updateBooking(Integer bookingId, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        // Don't allow updating cancelled or completed bookings
        if (existingBooking.getStatus().equals("CANCELLED") || existingBooking.getStatus().equals("COMPLETED")) {
            throw new IllegalStateException("Cannot update a booking that is already " + existingBooking.getStatus());
        }

        modelMapper.map(bookingDTO, existingBooking);
        existingBooking.setBookingId(bookingId);

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return modelMapper.map(updatedBooking, BookingResponseDTO.class);
    }

    @Override
    @Transactional
    public void cancelBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (!booking.getStatus().equals("PENDING") && !booking.getStatus().equals("CONFIRMED")) {
            throw new IllegalStateException("Only PENDING or CONFIRMED bookings can be cancelled");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    @Override
    public BookingResponseDTO confirmBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (!booking.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Only PENDING bookings can be confirmed");
        }

        booking.setStatus("CONFIRMED");
        Booking confirmedBooking = bookingRepository.save(booking);
        return modelMapper.map(confirmedBooking, BookingResponseDTO.class);
    }

    @Override
    public BookingResponseDTO completeBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (!booking.getStatus().equals("CONFIRMED")) {
            throw new IllegalStateException("Only CONFIRMED bookings can be completed");
        }

        booking.setStatus("COMPLETED");
        Booking completedBooking = bookingRepository.save(booking);
        return modelMapper.map(completedBooking, BookingResponseDTO.class);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByUser(Integer userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getBookingsByCar(Integer carId) {
        return bookingRepository.findByCarId(carId)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.plusDays(30);
        return bookingRepository.findByBookingDateBetweenAndStatus(now, endOfDay, "CONFIRMED")
                .stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }
}

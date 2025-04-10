package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUserId(Integer userId);

    List<Booking> findByCarId(Integer carId);

    List<Booking> findByStatus(String status);

    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate")
    List<Booking> findBookingsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByBookingDateBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, String status);
    List<Booking> findByCarIdAndBookingDateBetween(Integer cardId, LocalDateTime startDate, LocalDateTime endDate);

}

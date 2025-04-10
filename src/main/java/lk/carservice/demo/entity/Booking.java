package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "car_id", nullable = false)
    private Integer carId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "booking_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal bookingPrice;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELLED, COMPLETED

    @Column(name = "service_type", length = 50)
    private String serviceType;

    @Column(name = "notes", length = 255)
    private String notes;
}

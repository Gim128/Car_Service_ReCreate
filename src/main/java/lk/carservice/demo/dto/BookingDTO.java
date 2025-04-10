package lk.carservice.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    @NotNull(message = "Car Id")
    private Integer carId;

    @NotNull(message = "user id is required")
    private Integer userId;

    @NotNull(message = "Booking price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal bookingPrice;

    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date must be in present or future")
    private LocalDateTime bookingDate;

    @Size(max = 50, message = "Service type must be less than 50 characters")
    private String serviceType;

    @Size(max = 255, message = "Notes must be less than 255 characters")
    private String notes;

}

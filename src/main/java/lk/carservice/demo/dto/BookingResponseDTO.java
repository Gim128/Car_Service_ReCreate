package lk.carservice.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Integer bookingId;
    private Integer carId;
    private Integer userId;
    private BigDecimal bookingPrice;
    private LocalDateTime bookingDate;
    private String status;
    private String serviceType;
    private String notes;
}

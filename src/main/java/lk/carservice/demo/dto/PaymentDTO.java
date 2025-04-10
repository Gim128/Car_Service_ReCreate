package lk.carservice.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Paymemnt amount must be positive")
    private BigDecimal paymentAmount;

    @NotNull(message = "Payment date is required")
    private LocalDateTime paymentDate;

    @NotNull(message = "Buyer Id is required")
    private Integer buyerId;

    @NotNull(message = "Card Id is required")
    private Integer carId;
}

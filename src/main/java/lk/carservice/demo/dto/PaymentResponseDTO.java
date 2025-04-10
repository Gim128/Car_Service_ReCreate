package lk.carservice.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Integer paymentId;
    private BigDecimal paymentAmount;
    private LocalDateTime paymentDate;
    private Integer buyerId;
    private Integer carId;
}

package lk.carservice.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionDTO {

    @NotBlank(message = "Description is required")
    @Size(max = 45, message = "Description must be less than 45 characters")
    private String subscriptionDesc;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid amount format")
    private BigDecimal subscriptionAmount;

    @NotNull(message = "Number of ads is required")
    @Min(value = 1, message = "At least 1 ad is required")
    private Integer noOfAds;

    @NotNull(message = "User ID is required")
    private Integer userId;
}

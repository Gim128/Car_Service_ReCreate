package lk.carservice.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionResponseDTO {
    private int subscriptionId;
    private String subscriptionDesc;
    private BigDecimal subscriptionAmount;
    private int noOfAds;
    private int userId;
}

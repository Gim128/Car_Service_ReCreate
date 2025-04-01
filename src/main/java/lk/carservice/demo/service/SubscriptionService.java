package lk.carservice.demo.service;

import lk.carservice.demo.dto.SubscriptionDTO;
import lk.carservice.demo.dto.SubscriptionResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubscriptionService {
    SubscriptionResponseDTO createSubscription(SubscriptionDTO subscriptionDTO);
    SubscriptionResponseDTO getSubscriptionById(Integer subscriptionId);
    List<SubscriptionResponseDTO> getAllSubscriptions();
//    List<SubscriptionResponseDTO> getSubscriptionByUser(Integer userId);
    SubscriptionResponseDTO updateSubscription(int id, SubscriptionDTO subscriptionDTO);
    void deleteSubscription(int id);
    List<SubscriptionResponseDTO> getSubscriptionByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<SubscriptionResponseDTO> getSubscriptionsByUser(Integer userId);
}

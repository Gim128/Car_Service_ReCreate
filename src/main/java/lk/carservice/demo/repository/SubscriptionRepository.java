package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {
    List<Subscription> findByUserId(Integer userId);
    List<Subscription> findBySubscriptionAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
}

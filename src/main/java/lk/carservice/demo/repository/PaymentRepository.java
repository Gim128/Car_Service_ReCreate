package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByBuyerId(Integer buyerId);
    List<Payment> findByCarId(Integer carId);
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Payment> findByPaymentAmountGreaterThan(BigDecimal paymentAmount);
    List<Payment> findByPaymentAmountLessThan(BigDecimal paymentAmount);
    List<Payment> findByBuyerIdAndPaymentDateBetween(Integer buyerId, LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT SUM(p.paymentAmount) FROM Payment p WHERE p.buyerId = :buyerId")
    BigDecimal sumPaymentAmountByBuyerId(@Param("buyerId") Integer buyerId);

    List<Payment> findByPaymentAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    long countByCarId(Integer carId);

    Payment findFirstByBuyerIdOrderByPaymentDateDesc(Integer buyerId);

    Payment findFirstByBuyerIdOrderByPaymentAmountDesc(Integer buyerId);
}

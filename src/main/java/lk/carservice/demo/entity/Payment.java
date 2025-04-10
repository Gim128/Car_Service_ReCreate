package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "payment_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal paymentAmount;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @Column(name = "buyer_id", nullable = false)
    private Integer buyerId;

    @Column(name = "car_id", nullable = false)
    private Integer carId;
}

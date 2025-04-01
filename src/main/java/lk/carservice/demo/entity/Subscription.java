package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private int subscriptionId;

    @Column(name = "subscription_desc", length = 45, nullable = false)
    private String subscriptionDesc;

    @Column(name = "subscription_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal subscriptionAmount;

    @Column(name = "no_of_ads", nullable = false)
    private Integer noOfAds;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
}

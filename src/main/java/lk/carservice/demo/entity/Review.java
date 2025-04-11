package lk.carservice.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "seller_id", nullable = false)
    private Integer sellerId;

    @Column(name = "rating", nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(name = "reviewer_id", nullable = false)
    private Integer reviewerId;

    @CreationTimestamp
    @Column(name = "review_date", updatable = false)
    private LocalDateTime reviewDate;

    @Column(name = "is_approved")
    private Boolean isApproved = false;
}

package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findBySellerId(Integer sellerId);

    List<Review> findByReviewerId(Integer reviewerId);

    List<Review> findByIsApproved(Boolean isApproved);

    List<Review> findBySellerIdAndIsApproved(Integer sellerId, Boolean isApproved);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.sellerId = ?1 AND r.isApproved = true")
    Double findAverageRatingBySellerId(Integer sellerId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.sellerId = ?1 AND r.isApproved = true")
    Integer countApprovedReviewsBySellerId(Integer sellerId);

    boolean existsByReviewerIdAndSellerId(Integer reviewerId, Integer sellerId);
}

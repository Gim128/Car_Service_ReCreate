package lk.carservice.demo.service;

import lk.carservice.demo.dto.ReviewDTO;
import lk.carservice.demo.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewDTO reviewDTO, Integer reviewerId);
    ReviewResponseDTO getReviewById(Integer reviewId);
    List<ReviewResponseDTO> getReviewsBySeller(Integer sellerId);
    List<ReviewResponseDTO> getReviewsByReviewer(Integer reviewerId);
    List<ReviewResponseDTO> getPendingReviews();
    ReviewResponseDTO updateReview(Integer reviewId, ReviewDTO reviewDTO);
    void deleteReview(Integer reviewId);
    ReviewResponseDTO approveReview(Integer reviewId);
    ReviewResponseDTO rejectReview(Integer reviewId);
    Double getSellerAverageRating(Integer sellerId);
    Integer getSellerReviewCount(Integer sellerId);
}

package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.ReviewDTO;
import lk.carservice.demo.dto.ReviewResponseDTO;
import lk.carservice.demo.entity.Review;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.exceptions.ReviewModificationException;
import lk.carservice.demo.repository.ReviewRepository;
import lk.carservice.demo.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserService userService, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public ReviewResponseDTO createReview(ReviewDTO reviewDTO, Integer reviewerId) {
        if (reviewRepository.existsByReviewerIdAndSellerId(reviewerId, reviewDTO.getSellerId())) {
            throw new ReviewModificationException("You have already reviewed this seller");
        }

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setReviewerId(reviewerId);
        review.setIsApproved(false); // New reviews require approval

        Review savedReview = reviewRepository.save(review);
        return enrichReviewResponse(savedReview);
    }

    @Override
    public ReviewResponseDTO getReviewById(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        return enrichReviewResponse(review);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsBySeller(Integer sellerId) {
        return reviewRepository.findBySellerIdAndIsApproved(sellerId, true)
                .stream()
                .map(this::enrichReviewResponse)
                .collect(Collectors.toList());
    }



    @Override
    public List<ReviewResponseDTO> getReviewsByReviewer(Integer reviewerId) {
        return reviewRepository.findByReviewerId(reviewerId)
                .stream()
                .map(this::enrichReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getPendingReviews() {
        return reviewRepository.findByIsApproved(false)
                .stream()
                .map(this::enrichReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(Integer reviewId, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        if (existingReview.getIsApproved()) {
            throw new ReviewModificationException("Approved reviews cannot be modified");
        }

        modelMapper.map(reviewDTO, existingReview);
        existingReview.setReviewId(reviewId);
        existingReview.setIsApproved(false); // Require re-approval after update

        Review updatedReview = reviewRepository.save(existingReview);
        return enrichReviewResponse(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        if (review.getIsApproved()) {
            throw new ReviewModificationException("Approved reviews cannot be deleted");
        }

        reviewRepository.delete(review);
    }

    @Override
    @Transactional
    public ReviewResponseDTO approveReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        review.setIsApproved(true);
        Review approvedReview = reviewRepository.save(review);
        return enrichReviewResponse(approvedReview);
    }

    @Override
    @Transactional
    public ReviewResponseDTO rejectReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        review.setIsApproved(false);
        Review rejectedReview = reviewRepository.save(review);
        return enrichReviewResponse(rejectedReview);
    }

    private ReviewResponseDTO enrichReviewResponse(Review review) {
        ReviewResponseDTO response = modelMapper.map(review, ReviewResponseDTO.class);
        response.setReviewerName(userService.getUserNameById(review.getReviewerId()));
        return response;
    }

    @Override
    public Double getSellerAverageRating(Integer sellerId) {
        Double average = reviewRepository.findAverageRatingBySellerId(sellerId);
        return average != null ? average : 0.0;
    }

    @Override
    public Integer getSellerReviewCount(Integer sellerId) {
        return reviewRepository.countApprovedReviewsBySellerId(sellerId);
    }

    
}

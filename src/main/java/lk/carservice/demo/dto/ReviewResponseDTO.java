package lk.carservice.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {
    private Integer reviewId;
    private String description;
    private Integer sellerId;
    private Integer rating;
    private Integer reviewerId;
    private LocalDateTime reviewDate;
    private Boolean isApproved;
    private String reviewerName;
}

package lk.carservice.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleImageResponseDTO {
    private Integer imageId;
    private Integer cardId;
    private String imageUrl;
    private String contentType;
    private String fileName;
    private Boolean isPrimary;
    private LocalDateTime uploadDate;
}

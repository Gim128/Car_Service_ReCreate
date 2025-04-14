package lk.carservice.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminResponseDTO {
    private Integer adminId;
    private String username;
    private String email;
    private String fullName;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;
}

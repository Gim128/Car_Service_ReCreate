package lk.carservice.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer userType;
    private String address;
    private String mobile;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String userTypeName;
}

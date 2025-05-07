package lk.carservice.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String userName;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotNull(message = "User type is required")
    @Min(value = 1, message = "Invalid user type")
    @Max(value = 3, message = "Invalid user type")
    private Integer userType;

    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @Size(max = 20, message = "Mobile must be less than 20 characters")
    private String mobile;
}

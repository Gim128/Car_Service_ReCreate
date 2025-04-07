package lk.carservice.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceCenterDTO {

    @NotBlank(message = "name is required")
    @Size(max = 45, message = "name cannot be null")
    private String serviceCenterName;

    @NotBlank(message = "mobile number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid mobile number")
    private String serviceCenterMobile;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 45, message = "Email must be less than 45 characters")
    private String serviceCenterEmail;

    @NotBlank(message = "Location is required")
    @Size(max = 450, message = "Location must be less than 450 characters")
    private String serviceCenterLocation;

    private Boolean isActive = true;
}

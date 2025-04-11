package lk.carservice.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VehicleImageDTO {

    @NotNull(message = "Car ID is required")
    private Integer carId;

    @NotNull(message = "Image file is required")
    private MultipartFile imageFile;

    private Boolean isPrimary;
}

package lk.carservice.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CarDTO {
    @NotBlank(message = "Make is required")
    @Size(max = 50)
    private String make;

    @NotBlank(message = "Model is required")
    @Size(max = 50)
    private String model;

    @NotNull(message = "Year is required")
    @Min(1900) @Max(2100)
    private Integer year;

    @Size(max = 20)
    private String licensePlate;

    @Size(min = 17, max = 17)
    private String vin;

    @Size(max = 30)
    private String color;

    @Min(0)
    private Integer mileage;

    @NotNull
    private Integer userId;
}

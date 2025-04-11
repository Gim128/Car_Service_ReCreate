package lk.carservice.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Year;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer carId;

    @Column(name = "make", nullable = false, length = 50)
    @NotBlank(message = "Make is required")
    @Size(max = 50, message = "Make must be less than 50 characters")
    private String make;

    @Column(name = "model", nullable = false, length = 50)
    @NotBlank(message = "Model is required")
    @Size(max = 50, message = "Model must be less than 50 characters")
    private String model;

    @Column(name = "year", nullable = false)
    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be after 1900")
    @Max(value = 2100, message = "Year must be before 2100")
    private Integer year;

    @Column(name = "license_plate", unique = true, length = 20)
    @Size(max = 20, message = "License plate must be less than 20 characters")
    private String licensePlate;

    @Column(name = "vin", unique = true, length = 17)
    @Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    private String vin;

    @Column(name = "color", length = 30)
    @Size(max = 30, message = "Color must be less than 30 characters")
    private String color;

    @Column(name = "mileage")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

}

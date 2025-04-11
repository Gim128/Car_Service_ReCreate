package lk.carservice.demo.dto;

import lombok.Data;

@Data
public class CarResponseDTO {
    private Integer carId;
    private String make;
    private String model;
    private Integer year;
    private String licensePlate;
    private String vin;
    private String color;
    private Integer mileage;
    private Boolean isActive;
    private Integer userId;
    private String ownerName;
}

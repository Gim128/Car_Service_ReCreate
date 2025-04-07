package lk.carservice.demo.dto;

import lombok.Data;

@Data
public class ServiceCenterResponseDTO {
    private Integer serviceCenterId;
    private String serviceCenterName;
    private String serviceCenterMobile;
    private String serviceCenterEmail;
    private String serviceCenterLocation;
    private Boolean isActive;
}

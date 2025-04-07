package lk.carservice.demo.dto;

import lombok.Data;

@Data
public class BrandResponseDTO {
    private Long brandId;
    private String brandName;
    private String brandDesc;
    private Boolean isBrandActive;
}

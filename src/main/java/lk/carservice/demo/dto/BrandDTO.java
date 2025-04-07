package lk.carservice.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BrandDTO {

    private Long brandId;

    @NotBlank(message = "Brand name is requiered")
    @Size(max = 45, message = "brand name must be less than 45 characters")
    private String brandName;

    @Size(message = "Brand description must be less than 45 characters")
    private String brandDesc;

    private Boolean isBrandActive;

}

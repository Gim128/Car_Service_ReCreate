package lk.carservice.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SparePartResponseDTO {
    private Integer sparePartId;
    private String sparePartName;
    private String sparePartDescription;
    private Integer sparePartCategoryId;
    private Integer sparePartsBrand;
    private Boolean isSparePartActive;
    private BigDecimal sparePartPrice;
    private Integer sparePartQty;
    private Boolean isActive;
}

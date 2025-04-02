package lk.carservice.demo.dto;

import lombok.Data;

@Data
public class SparePartsCategoryResponseDTO {
    private Integer sparePartsCategoryId;
    private String sparePartsCategoryName;
    private String sparePartsCategoryDescription;
    private boolean isCategoryActive;
}

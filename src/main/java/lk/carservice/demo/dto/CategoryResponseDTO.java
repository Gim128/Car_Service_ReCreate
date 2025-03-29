package lk.carservice.demo.dto;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private Boolean isActive;
}

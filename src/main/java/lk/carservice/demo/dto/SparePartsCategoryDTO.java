package lk.carservice.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SparePartsCategoryDTO {

    private Integer categoryId;

    @NotBlank(message = "Category name is requied")
    @Size(max = 45, message = "Category name cannot be null")
    private String sparePartsCategoryName;

    @Size(max = 450, message = "Category description cannot be null")
    private String sparePartDescription;

    private Boolean isCategoryActive = true;
}

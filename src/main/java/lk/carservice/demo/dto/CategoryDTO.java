package lk.carservice.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {

    private Integer categoryId;

    @NotBlank(message = "Category name is required")
    @Size(max = 45, message = "Category name must be less than 45 characters")
    private String categoryName;

    @Size(max = 450, message = "Description must be less than 450 characters")
    private String categoryDescription;

    private Boolean isActive = true;
}

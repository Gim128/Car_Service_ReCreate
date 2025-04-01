package lk.carservice.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SparePartDTO {

    @NotBlank(message = "Part name is required")
    @Size(max = 45, message = "Part name must be less than 45 characters")
    private String sparePartName;

    @Size(max = 450, message = "Description must be less than 450 characters")
    private String sparePartDescription;

    @NotNull(message = "Category ID is required")
    private Integer sparePartCategoryId;

    @NotNull(message = "Brand ID is required")
    private Integer sparePartsBrand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid price format")
    private BigDecimal sparePartPrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer sparePartQty;

    private Boolean isSparePartActive = true;
    private Boolean isActive = true;


}

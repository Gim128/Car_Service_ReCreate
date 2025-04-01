package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spare_Part")
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spare_part_id")
    private Integer sparePartId;

    @Column(name = "spare_part_name", length = 45, nullable = false)
    private String sparePartName;

    @Column(name = "spare_part_description", length = 450)
    private String sparePartDescription;

    @Column(name = "spare_part_category_id")
    private Integer sparePartCategoryId;

    @Column(name = "spare_parts_brand")
    private Integer sparePartsBrand;

    @Column(name = "is_spare_part_active", columnDefinition = "TINYINT default 1")
    private Boolean isSparePartActive = true;

    @Column(name = "spare_part_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal sparePartPrice;

    @Column(name = "spare_part_qty", nullable = false)
    private Integer sparePartQty;

    @Column(name = "is_active", columnDefinition = "TINYINT default 1")
    private Boolean isActive = true;


}

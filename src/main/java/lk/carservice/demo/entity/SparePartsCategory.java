package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spare_parts_category")
public class SparePartsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spare_parts_cat_id")
    private Integer sparePartsCategoryId;

    @Column(name = "spare_part_category_name", length = 45, nullable = false)
    private String sparePartsCategoryName;

    @Column(name = "part_desc", length = 45, nullable = false)
    private String sparePartDescription;

    @Column(name = "is_category_active", columnDefinition = "TINYINT default 1")
    private Boolean isCategoryActive = true;

    public void setSparePartCategoryId(Integer id) {

    }
}

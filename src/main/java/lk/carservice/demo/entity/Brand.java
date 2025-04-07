package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name", length = 56, nullable = false)
    private String brandName;

    @Column(name = "brand_desc", length = 45)
    private String brandDesc;

    @Column(name = "is_brand_active")
    private Boolean isBrandActive = true;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}

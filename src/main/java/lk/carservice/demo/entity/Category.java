package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name", length = 45, nullable = false)
    private String categoryName;

    @Column(name = "category_description", length = 450)
    private String categoryDescription;

    @Column(name = "is_active", columnDefinition = "TINYINT default 1")
    private Boolean isActive = true;

    @Column(name = "is_deleted", columnDefinition = "TINYINT default 0")
    private Boolean isDeleted = false;
}

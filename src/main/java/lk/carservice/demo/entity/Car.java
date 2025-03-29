package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Year;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", nullable = false)
    private Long car_id;

    @Column(name = "Car_name", nullable = false)
    private String name;

    @Column(name = "car_description", nullable = false)
    private String description;

    @Column(name = "car_category", nullable = false)
    private String category;

    @Column(name = "car_manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "car_price", nullable = false)
    private Double price;

    @Column(name = "manufactured_year", nullable = false)
    private Year Manufactured_Year;

    @Column(name = "registered_year", nullable = false)
    private Year registeredYear;

    @Column(name = "is_active", columnDefinition = "TINYINT(1) default 1")
    private Boolean isActive = true;

}

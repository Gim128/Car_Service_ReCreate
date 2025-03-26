package lk.carservice.demo.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.Year;

@Data
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long car_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private Double price;

    @Column(name = "manufactured_year", nullable = false)
    private Year Manufactured_Year;

    @Column(name = "registered_year", nullable = false)
    private Year registeredYear;

    @Column(nullable = false)
    private Boolean isActive = true;

//    public Year getRegisteredYear() {
//        return this.Registered_Year;
//    }
//
//    public void setRegisteredYear(Year registeredYear) {
//        this.Registered_Year = registeredYear;
//    }

}

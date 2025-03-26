package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
//    List<Car> findByCarId(Long car_id);
//    Optional<Car> findByCarId(Long carId);
    List<Car> findByRegisteredYear(Year year);
    List<Car> findByRegisteredYearBetween(Year start, Year end);
    List<Car> findByCategory(String category);
    List<Car> findByManufacturer(String manufacturer);
    Car findByName(String name);
    List<Car> findByIsActive(Boolean isActive);
}

package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByUserId(Integer userId);

    List<Car> findByMake(String make);

    List<Car> findByMakeAndModel(String make, String model);

    List<Car> findByYearBetween(Integer startYear, Integer endYear);

    boolean existsByLicensePlate(String licensePlate);

    boolean existsByVin(String vin);
}

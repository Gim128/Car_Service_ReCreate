package lk.carservice.demo.repository;

import lk.carservice.demo.entity.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleImageRepository extends JpaRepository<VehicleImage, Long> {

    List<VehicleImage> findByCarId(Integer carId);

    List<VehicleImage> findByCarIdAndIsPrimary(Integer carId, Boolean isPrimary);

    void deleteByCarId(Integer carId);
}

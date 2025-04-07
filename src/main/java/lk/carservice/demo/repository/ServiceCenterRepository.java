package lk.carservice.demo.repository;

import lk.carservice.demo.entity.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Integer> {
    List<ServiceCenter> findByIsActiveTrue();
    Optional<ServiceCenter> findByServiceCenterEmail(String email);
    List<ServiceCenter> findByServiceCenterLocationContainingIgnoreCase(String location);
    List<ServiceCenter> findByServiceCenterNameContainingIgnoreCase(String name);
}

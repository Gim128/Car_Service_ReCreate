package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByIsDeletedFalse();

    List<Brand> findByIsBrandActiveTrueAndIsDeletedFalse();

    boolean existsByBrandName(String brandName);
}

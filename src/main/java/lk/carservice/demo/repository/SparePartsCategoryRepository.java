package lk.carservice.demo.repository;

import lk.carservice.demo.entity.SparePartsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartsCategoryRepository extends JpaRepository<SparePartsCategory,Integer> {

    List<SparePartsCategory> findBySparePartsCategoryId(Integer sparePartsCategoryId);
    List<SparePartsCategory> findByIsCategoryActiveTrue();
    List<SparePartsCategory> findBySparePartsCategoryNameContainingIgnoreCase(String name);
}

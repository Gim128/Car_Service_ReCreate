package lk.carservice.demo.repository;

import lk.carservice.demo.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer> {
    List<SparePart> findBySparePartCategoryId(Integer categoryId);
    List<SparePart> findBySparePartsBrand(Integer brandId);
    List<SparePart> findByIsActiveTrue();
    List<SparePart> findByIsSparePartActiveTrue();

    @Query("SELECT sp FROM SparePart sp WHERE " +
            "(:name IS NULL OR sp.sparePartName LIKE %:name%) AND " +
            "(:minPrice IS NULL OR sp.sparePartPrice >= :minPrice) AND " +
            "(:maxPrice IS NULL OR sp.sparePartPrice <= :maxPrice)")
    List<SparePart> searchSpareParts(
            @Param("name") String name,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);
}

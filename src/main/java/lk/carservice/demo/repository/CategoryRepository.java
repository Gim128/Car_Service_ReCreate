package lk.carservice.demo.repository;

import lk.carservice.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByIsActiveTrue();
    List<Category> findByCategoryNameContainingIgnoreCase(String name);
}

package lk.carservice.demo.service;

import lk.carservice.demo.dto.CategoryDTO;
import lk.carservice.demo.dto.SparePartResponseDTO;
import lk.carservice.demo.dto.SparePartsCategoryDTO;
import lk.carservice.demo.dto.SparePartsCategoryResponseDTO;

import java.util.List;

public interface SparePartsCategoryService {
    SparePartsCategoryResponseDTO createCategory(SparePartsCategoryDTO categoryDTO);

    SparePartsCategoryResponseDTO createCategory(CategoryDTO categoryDTO);

    SparePartResponseDTO getCategoryById(Integer id);
    List<SparePartsCategoryResponseDTO> getAllCategories();
    List<SparePartsCategoryResponseDTO> getActiveCategories();
    SparePartsCategoryResponseDTO updateCategory(Integer id, SparePartsCategoryDTO categoryDTO);
    void deleteCategory(Integer id);
    void toggleCategoryStatus(Integer id, Boolean status);
}

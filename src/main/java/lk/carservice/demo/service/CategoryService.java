package lk.carservice.demo.service;

import lk.carservice.demo.dto.CategoryDTO;
import lk.carservice.demo.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryDTO categoryDTO);
    CategoryResponseDTO getCategoryById(Integer id);
    List<CategoryResponseDTO> getAllCategories();
    List<CategoryResponseDTO> getActiveCategories();
    CategoryResponseDTO updateCategory(Integer id, CategoryDTO categoryDTO);
    void deleteCategory(Integer id);
    void toggleCategoryStatus(Integer id, Boolean status);
}

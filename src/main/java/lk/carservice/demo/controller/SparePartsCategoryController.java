package lk.carservice.demo.controller;


import jakarta.validation.Valid;
import lk.carservice.demo.dto.SparePartResponseDTO;
import lk.carservice.demo.dto.SparePartsCategoryDTO;
import lk.carservice.demo.dto.SparePartsCategoryResponseDTO;
import lk.carservice.demo.service.SparePartsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spare-parts-categories")
public class SparePartsCategoryController {

    private final SparePartsCategoryService sparePartsCategoryService;

    @Autowired
    public SparePartsCategoryController(SparePartsCategoryService sparePartsCategoryService) {
        this.sparePartsCategoryService = sparePartsCategoryService;
    }

    @PostMapping
    public ResponseEntity<SparePartsCategoryResponseDTO> createCategory(
            @Valid @RequestBody SparePartsCategoryDTO categoryDTO) {
        SparePartsCategoryResponseDTO createdCategory = sparePartsCategoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartResponseDTO> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(sparePartsCategoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<SparePartsCategoryResponseDTO>> getAllCategories(
            @RequestParam(required = false) Boolean activeOnly) {
        List<SparePartsCategoryResponseDTO> categories = activeOnly != null && activeOnly ?
                sparePartsCategoryService.getActiveCategories() :
                sparePartsCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePartsCategoryResponseDTO> updateCategory(
            @PathVariable Integer id, @Valid @RequestBody SparePartsCategoryDTO categoryDTO) {
        return ResponseEntity.ok(sparePartsCategoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        sparePartsCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleCategoryStatus(
            @PathVariable Integer id, @RequestParam Boolean active) {
        sparePartsCategoryService.toggleCategoryStatus(id, active);
        return ResponseEntity.noContent().build();
    }
}

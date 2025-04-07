package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.BrandDTO;
import lk.carservice.demo.dto.BrandResponseDTO;
import lk.carservice.demo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<BrandResponseDTO> createBrand(@Valid @RequestBody BrandDTO brandDTO) {
        BrandResponseDTO createdBrand = brandService.createBrand(brandDTO);
        return new ResponseEntity<>(createdBrand, HttpStatus.CREATED);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable Integer brandId) {
        BrandResponseDTO brand = brandService.getBrandById(Long.valueOf(brandId));
        return ResponseEntity.ok(brand);
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands(
            @RequestParam(required = false) Boolean activeOnly) {

        List<BrandResponseDTO> brands;
        if (activeOnly != null && activeOnly) {
            brands = brandService.getAllActiveBrands();
        } else {
            brands = brandService.getAllBrands();
        }
        return ResponseEntity.ok(brands);
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandResponseDTO> updateBrand(
            @PathVariable Integer brandId,
            @Valid @RequestBody BrandDTO brandDTO) {

        BrandResponseDTO updatedBrand = brandService.updateBrand(brandId, brandDTO);
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Integer brandId) {
        brandService.deleteBrand(Long.valueOf(brandId));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{brandId}/activate")
    public ResponseEntity<Void> activateBrand(@PathVariable Integer brandId) {
        brandService.activateBrand(Long.valueOf(brandId));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{brandId}/deactivate")
    public ResponseEntity<Void> deactivateBrand(@PathVariable Integer brandId) {
        brandService.deactivateBrand(Long.valueOf(brandId));
        return ResponseEntity.noContent().build();
    }
}

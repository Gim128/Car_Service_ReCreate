package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.SparePartDTO;
import lk.carservice.demo.dto.SparePartResponseDTO;
import lk.carservice.demo.service.SparePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/spare-parts")
public class SparePartController {

    private final SparePartService sparePartService;

    @Autowired
    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    @PostMapping
    public ResponseEntity<SparePartResponseDTO> createSparePart(@Valid @RequestBody SparePartDTO sparePartDTO) {
        SparePartResponseDTO createdSparePart = sparePartService.createSparePart(sparePartDTO);
        return new ResponseEntity<>(createdSparePart, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartResponseDTO> getSparePartById(@PathVariable Integer id) {
        return ResponseEntity.ok(sparePartService.getSparePartById(id));
    }

    @GetMapping
    public ResponseEntity<List<SparePartResponseDTO>> getAllSpareParts(
            @RequestParam(required = false) Boolean activeOnly,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        if (categoryId != null) {
            return ResponseEntity.ok(sparePartService.getSparePartsByCategory(categoryId));
        } else if (brandId != null) {
            return ResponseEntity.ok(sparePartService.getSparePartsByBrand(brandId));
        } else if (name != null || minPrice != null || maxPrice != null) {
            return ResponseEntity.ok(sparePartService.searchSpareParts(name, minPrice, maxPrice));
        } else if (activeOnly != null && activeOnly) {
            return ResponseEntity.ok(sparePartService.getActiveSpareParts());
        } else {
            return ResponseEntity.ok(sparePartService.getAllSpareParts());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePartResponseDTO> updateSparePart(
            @PathVariable Integer id, @Valid @RequestBody SparePartDTO sparePartDTO) {
        return ResponseEntity.ok(sparePartService.updateSparePart(id, sparePartDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSparePart(@PathVariable Integer id) {
        sparePartService.deleteSparePart(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleSparePartStatus(
            @PathVariable Integer id, @RequestParam Boolean active) {
        sparePartService.toggleSparePartStatus(id, active);
        return ResponseEntity.noContent().build();
    }

}

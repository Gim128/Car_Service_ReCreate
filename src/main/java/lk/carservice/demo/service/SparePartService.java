package lk.carservice.demo.service;

import lk.carservice.demo.dto.SparePartDTO;
import lk.carservice.demo.dto.SparePartResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SparePartService {
    SparePartResponseDTO createSparePart(SparePartDTO sparePartDTO);
    SparePartResponseDTO getSparePartById(Integer id);
    List<SparePartResponseDTO> getAllSpareParts();
    List<SparePartResponseDTO> getActiveSpareParts();
    List<SparePartResponseDTO> searchSpareParts(String name, BigDecimal minPrice, BigDecimal maxPrice);
    List<SparePartResponseDTO> getSparePartsByCategory(Integer categoryId);
    List<SparePartResponseDTO> getSparePartsByBrand(Integer brandId);
    SparePartResponseDTO updateSparePart(Integer id, SparePartDTO sparePartDTO);
    void deleteSparePart(Integer id);
    void toggleSparePartStatus(Integer id, Boolean isActive);

}

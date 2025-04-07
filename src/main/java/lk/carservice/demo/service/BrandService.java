package lk.carservice.demo.service;

import lk.carservice.demo.dto.BrandDTO;
import lk.carservice.demo.dto.BrandResponseDTO;

import java.util.List;

public interface BrandService {

    BrandResponseDTO createBrand(BrandDTO brandDTO);

    BrandResponseDTO getBrandById(Long brandId);

    List<BrandResponseDTO> getAllBrands();

    List<BrandResponseDTO> getAllActiveBrands();

    BrandResponseDTO updateBrand(Long brandId, BrandDTO brandDTO);

    void deleteBrand(Long brandId);

    void activateBrand(Long brandId);

    void deactivateBrand(Long brandId);

    BrandResponseDTO updateBrand(Integer brandId, BrandDTO brandDTO);
}

package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.BrandDTO;
import lk.carservice.demo.dto.BrandResponseDTO;
import lk.carservice.demo.entity.Brand;
import lk.carservice.demo.exceptions.ResourceAlreadyExistsException;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.BrandRepository;
import lk.carservice.demo.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public BrandResponseDTO createBrand(BrandDTO brandDTO) {
        if (brandRepository.existsByBrandName(brandDTO.getBrandName())) {
            throw new ResourceAlreadyExistsException("Brand with name '" + brandDTO.getBrandName() + "' already exists");
        }

        Brand brand = modelMapper.map(brandDTO, Brand.class);
        brand.setIsDeleted(false);
        if (brand.getIsBrandActive() == null) {
            brand.setIsBrandActive(true);
        }

        Brand savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand, BrandResponseDTO.class);
    }

    @Override
    public BrandResponseDTO getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));

        if (brand.getIsDeleted()) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }

        return modelMapper.map(brand, BrandResponseDTO.class);
    }

    @Override
    public List<BrandResponseDTO> getAllBrands() {
        return brandRepository.findByIsDeletedFalse()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandResponseDTO> getAllActiveBrands() {
        return brandRepository.findByIsBrandActiveTrueAndIsDeletedFalse()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponseDTO updateBrand(Long brandId, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));

        if (existingBrand.getIsDeleted()) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }

        // Check if new brand name conflicts with existing brands (excluding current one)
        if (brandDTO.getBrandName() != null &&
                !brandDTO.getBrandName().equals(existingBrand.getBrandName()) &&
                brandRepository.existsByBrandName(brandDTO.getBrandName())) {
            throw new ResourceAlreadyExistsException("Brand with name '" + brandDTO.getBrandName() + "' already exists");
        }

        modelMapper.map(brandDTO, existingBrand);
        existingBrand.setBrandId(brandId); // Ensure ID remains the same

        Brand updatedBrand = brandRepository.save(existingBrand);
        return modelMapper.map(updatedBrand, BrandResponseDTO.class);
    }

    @Override
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));

        if (!brand.getIsDeleted()) {
            brand.setIsDeleted(true);
            brandRepository.save(brand);
        }
    }

    @Override
    public void activateBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));

        if (brand.getIsDeleted()) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }

        brand.setIsBrandActive(true);
        brandRepository.save(brand);
    }

    @Override
    public void deactivateBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));

        if (brand.getIsDeleted()) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }

        brand.setIsBrandActive(false);
        brandRepository.save(brand);
    }

    @Override
    public BrandResponseDTO updateBrand(Integer brandId, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(Long.valueOf(brandId))
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));

        if (existingBrand.getIsDeleted()) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }

        // Check if new brand name conflicts with existing brands (excluding current one)
        if (brandDTO.getBrandName() != null &&
                !brandDTO.getBrandName().equals(existingBrand.getBrandName()) &&
                brandRepository.existsByBrandName(brandDTO.getBrandName())) {
            throw new ResourceAlreadyExistsException("Brand with name '" + brandDTO.getBrandName() + "' already exists");
        }

        modelMapper.map(brandDTO, existingBrand);
        existingBrand.setBrandId(Long.valueOf(brandId)); // Ensure ID remains the same

        Brand updatedBrand = brandRepository.save(existingBrand);
        return modelMapper.map(updatedBrand, BrandResponseDTO.class);
    }
}

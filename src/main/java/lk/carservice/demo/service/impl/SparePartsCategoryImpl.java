package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.*;
import lk.carservice.demo.entity.SparePartsCategory;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.SparePartsCategoryRepository;
import lk.carservice.demo.service.SparePartsCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SparePartsCategoryImpl implements SparePartsCategoryService {

    private final SparePartsCategoryRepository sparePartsCategoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SparePartsCategoryImpl(SparePartsCategoryRepository sparePartsCategoryRepository, ModelMapper modelMapper) {
        this.sparePartsCategoryRepository = sparePartsCategoryRepository;
        this.modelMapper = modelMapper;
    }

//    @Override
//    public SparePartsCategoryResponseDTO createCategory(SparePartsCategoryDTO sparePartsCategoryDTO) {
//        SparePartsCategory sparePartsCategory = modelMapper.map(sparePartsCategoryDTO, SparePartsCategory.class);
//        SparePartsCategory savedCategory = sparePartsCategoryRepository.save(sparePartsCategory);
//        return modelMapper.map(savedCategory, SparePartsCategoryResponseDTO.class);
//    }

    @Override
    public SparePartsCategoryResponseDTO createCategory(SparePartsCategoryDTO sparePartsCategoryDTO) {
        SparePartsCategory sparePartsCategory = modelMapper.map(sparePartsCategoryDTO, SparePartsCategory.class);
        SparePartsCategory savedCategory = sparePartsCategoryRepository.save(sparePartsCategory);
        return modelMapper.map(savedCategory, SparePartsCategoryResponseDTO.class);
    }

    @Override
    public SparePartsCategoryResponseDTO createCategory(CategoryDTO categoryDTO) {
        SparePartsCategory sparePartsCategory = modelMapper.map(categoryDTO, SparePartsCategory.class);
        SparePartsCategory savedCategory = sparePartsCategoryRepository.save(sparePartsCategory);
        return modelMapper.map(savedCategory, SparePartsCategoryResponseDTO.class);
    }


    @Override
    public SparePartResponseDTO getCategoryById(Integer id) {
        Optional<SparePartsCategory> partsCategory = Optional.ofNullable(sparePartsCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spare part not found with id: " + id)));
        return modelMapper.map(partsCategory, SparePartResponseDTO.class);
    }

    @Override
    public List<SparePartsCategoryResponseDTO> getAllCategories() {
        return sparePartsCategoryRepository.findAll().stream()
                .map(sparePartsCategory -> modelMapper.map(sparePartsCategory, SparePartsCategoryResponseDTO.class))
                .collect(Collectors.toList()).reversed();
    }

    @Override
    public List<SparePartsCategoryResponseDTO> getActiveCategories() {
        return sparePartsCategoryRepository.findByIsCategoryActiveTrue().stream()
                .map(category -> modelMapper.map(category, SparePartsCategoryResponseDTO.class))
                .collect(Collectors.toList()).reversed();
    }

    @Override
    public SparePartsCategoryResponseDTO updateCategory(Integer id, SparePartsCategoryDTO categoryDTO) {
        SparePartsCategory existingCategory = sparePartsCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        modelMapper.map(categoryDTO, existingCategory);
        existingCategory.setSparePartCategoryId(id);
        SparePartsCategory updatedCategory = sparePartsCategoryRepository.save(existingCategory);

        return modelMapper.map(updatedCategory, SparePartsCategoryResponseDTO.class);
    }

//    @Override
//    public SparePartsCategoryResponseDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
//        SparePartsCategory existingCategory = sparePartsCategoryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
//
//        modelMapper.map(categoryDTO, existingCategory);
//        existingCategory.setSparePartCategoryId(id);
//        SparePartsCategory updatedCategory = sparePartsCategoryRepository.save(existingCategory);
//
//        return modelMapper.map(updatedCategory, SparePartsCategoryResponseDTO.class);
//    }

    @Override
    public void deleteCategory(Integer id) {
        SparePartsCategory category = sparePartsCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setIsCategoryActive(false);
        sparePartsCategoryRepository.save(category);
    }

    @Override
    public void toggleCategoryStatus(Integer id, Boolean status) {
        SparePartsCategory category = sparePartsCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setIsCategoryActive(status);
        sparePartsCategoryRepository.save(category);
    }
}

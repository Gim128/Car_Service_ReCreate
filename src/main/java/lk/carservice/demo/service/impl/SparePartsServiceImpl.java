package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.SparePartDTO;
import lk.carservice.demo.dto.SparePartResponseDTO;
import lk.carservice.demo.entity.SparePart;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.SparePartRepository;
import lk.carservice.demo.service.SparePartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SparePartsServiceImpl implements SparePartService {

    private final SparePartRepository sparePartRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SparePartsServiceImpl(SparePartRepository sparePartsRepository, ModelMapper modelMapper) {
        this.sparePartRepository = sparePartsRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public SparePartResponseDTO createSparePart(SparePartDTO sparePartDTO) {
        SparePart sparePart = modelMapper.map(sparePartDTO, SparePart.class);
        SparePart savedSparePart = sparePartRepository.save(sparePart);
        return modelMapper.map(savedSparePart, SparePartResponseDTO.class);
    }

    @Override
    public SparePartResponseDTO getSparePartById(Integer id) {
        SparePart sparePart = sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spare part not found with id: " + id));
        return modelMapper.map(sparePart, SparePartResponseDTO.class);
    }

    @Override
    public List<SparePartResponseDTO> getAllSpareParts() {
        return sparePartRepository.findAll().stream()
                .map(sparePart -> modelMapper.map(sparePart, SparePartResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartResponseDTO> getActiveSpareParts() {
        return sparePartRepository.findByIsActiveTrue().stream()
                .map(sparePart -> modelMapper.map(sparePart, SparePartResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartResponseDTO> searchSpareParts(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        return sparePartRepository.searchSpareParts(name, minPrice, maxPrice).stream()
                .map(sparePart -> modelMapper.map(sparePart, SparePartResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartResponseDTO> getSparePartsByCategory(Integer categoryId) {
        return sparePartRepository.findBySparePartCategoryId(categoryId).stream()
                .map(sparePart -> modelMapper.map(sparePart, SparePartResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartResponseDTO> getSparePartsByBrand(Integer brandId) {
        return sparePartRepository.findBySparePartsBrand(brandId).stream()
                .map(sparePart -> modelMapper.map(sparePart, SparePartResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SparePartResponseDTO updateSparePart(Integer id, SparePartDTO sparePartDTO) {
        SparePart existingSparePart = sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spare part not found with id: " + id));

        modelMapper.map(sparePartDTO, existingSparePart);
        existingSparePart.setSparePartId(id);
        SparePart updatedSparePart = sparePartRepository.save(existingSparePart);

        return modelMapper.map(updatedSparePart, SparePartResponseDTO.class);
    }

    @Override
    public void deleteSparePart(Integer id) {
        SparePart sparePart = sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spare part not found with id: " + id));
        sparePart.setIsActive(false);
        sparePartRepository.save(sparePart);
    }

    @Override
    public void toggleSparePartStatus(Integer id, Boolean isActive) {
        SparePart sparePart = sparePartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spare part not found with id: " + id));
        sparePart.setIsSparePartActive(isActive);
        sparePartRepository.save(sparePart);
    }
}

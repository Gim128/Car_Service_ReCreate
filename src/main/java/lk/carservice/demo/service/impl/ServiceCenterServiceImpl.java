package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.ServiceCenterDTO;
import lk.carservice.demo.dto.ServiceCenterResponseDTO;
import lk.carservice.demo.entity.ServiceCenter;
import lk.carservice.demo.exceptions.DuplicateResourceException;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.ServiceCenterRepository;
import lk.carservice.demo.service.ServiceCenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceCenterServiceImpl implements ServiceCenterService {

    private final ServiceCenterRepository serviceCenterRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceCenterServiceImpl(ServiceCenterRepository serviceCenterRepository, ModelMapper modelMapper) {
        this.serviceCenterRepository = serviceCenterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceCenterResponseDTO registerServiceCenter(ServiceCenterDTO serviceCenterDTO) {
        // Check if email already exists
        if (serviceCenterRepository.findByServiceCenterEmail(serviceCenterDTO.getServiceCenterEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered with another service center");
        }

        ServiceCenter serviceCenter = modelMapper.map(serviceCenterDTO, ServiceCenter.class);
        ServiceCenter savedServiceCenter = serviceCenterRepository.save(serviceCenter);
        return modelMapper.map(savedServiceCenter, ServiceCenterResponseDTO.class);
    }

    @Override
    public ServiceCenterResponseDTO createServiceCenter(ServiceCenterDTO serviceCenterDTO) {
        // Check if email already exists
        if (serviceCenterRepository.findByServiceCenterEmail(serviceCenterDTO.getServiceCenterEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered with another service center");
        }

        ServiceCenter serviceCenter = modelMapper.map(serviceCenterDTO, ServiceCenter.class);
        ServiceCenter savedServiceCenter = serviceCenterRepository.save(serviceCenter);
        return modelMapper.map(savedServiceCenter, ServiceCenterResponseDTO.class);
    }

    @Override
    public ServiceCenterResponseDTO getServiceCenterById(Integer id) {
        ServiceCenter serviceCenter = serviceCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service center not found with id: " + id));
        return modelMapper.map(serviceCenter, ServiceCenterResponseDTO.class);
    }

    @Override
    public List<ServiceCenterResponseDTO> getAllServiceCenters() {
        return serviceCenterRepository.findAll().stream()
                .map(serviceCenter -> modelMapper.map(serviceCenter, ServiceCenterResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceCenterResponseDTO> getActiveServiceCenters() {
        return serviceCenterRepository.findByIsActiveTrue().stream()
                .map(serviceCenter -> modelMapper.map(serviceCenter, ServiceCenterResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceCenterResponseDTO updateServiceCenter(Integer id, ServiceCenterDTO serviceCenterDTO) {
        ServiceCenter existingServiceCenter = serviceCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service center not found with id: " + id));

        // Check if email is being changed to an existing one
        if (!existingServiceCenter.getServiceCenterEmail().equals(serviceCenterDTO.getServiceCenterEmail())) {
            if (serviceCenterRepository.findByServiceCenterEmail(serviceCenterDTO.getServiceCenterEmail()).isPresent()) {
                throw new DuplicateResourceException("Email already registered with another service center");
            }
        }

        modelMapper.map(serviceCenterDTO, existingServiceCenter);
        existingServiceCenter.setServiceCenterId(id); // Ensure ID remains the same
        ServiceCenter updatedServiceCenter = serviceCenterRepository.save(existingServiceCenter);

        return modelMapper.map(updatedServiceCenter, ServiceCenterResponseDTO.class);
    }

    @Override
    public void deleteServiceCenter(Integer id) {
        ServiceCenter serviceCenter = serviceCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service center not found with id: " + id));
        serviceCenterRepository.delete(serviceCenter);
    }

    @Override
    public void toggleServiceCenterStatus(Integer id, Boolean status) {
        ServiceCenter serviceCenter = serviceCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service center not found with id: " + id));
        serviceCenter.setIsActive(status);
        serviceCenterRepository.save(serviceCenter);
    }

    @Override
    public List<ServiceCenterResponseDTO> searchServiceCenters(String query) {
        List<ServiceCenter> byName = serviceCenterRepository.findByServiceCenterNameContainingIgnoreCase(query);
        List<ServiceCenter> byLocation = serviceCenterRepository.findByServiceCenterLocationContainingIgnoreCase(query);

        // Combine and remove duplicates
        byName.addAll(byLocation.stream()
                .filter(sc -> !byName.contains(sc))
                .toList());

        return byName.stream()
                .map(serviceCenter -> modelMapper.map(serviceCenter, ServiceCenterResponseDTO.class))
                .collect(Collectors.toList());
    }
}

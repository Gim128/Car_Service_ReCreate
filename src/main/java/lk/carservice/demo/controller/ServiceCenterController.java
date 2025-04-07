package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.ServiceCenterDTO;
import lk.carservice.demo.dto.ServiceCenterResponseDTO;
import lk.carservice.demo.service.ServiceCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-centers")
public class ServiceCenterController {

    private final ServiceCenterService serviceCenterService;

    @Autowired
    public ServiceCenterController(ServiceCenterService serviceCenterService) {
        this.serviceCenterService = serviceCenterService;
    }

    @PostMapping
    public ResponseEntity<ServiceCenterResponseDTO> createServiceCenter(
            @Valid @RequestBody ServiceCenterDTO serviceCenterDTO) {
        ServiceCenterResponseDTO createdServiceCenter = serviceCenterService.createServiceCenter(serviceCenterDTO);
        return new ResponseEntity<>(createdServiceCenter, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCenterResponseDTO> getServiceCenterById(@PathVariable Integer id) {
        ServiceCenterResponseDTO serviceCenter = serviceCenterService.getServiceCenterById(id);
        return ResponseEntity.ok(serviceCenter);
    }

    @GetMapping
    public ResponseEntity<List<ServiceCenterResponseDTO>> getAllServiceCenters(
            @RequestParam(required = false) Boolean activeOnly,
            @RequestParam(required = false) String search) {

        List<ServiceCenterResponseDTO> serviceCenters;

        if (search != null && !search.isEmpty()) {
            serviceCenters = serviceCenterService.searchServiceCenters(search);
        } else if (activeOnly != null && activeOnly) {
            serviceCenters = serviceCenterService.getActiveServiceCenters();
        } else {
            serviceCenters = serviceCenterService.getAllServiceCenters();
        }

        return ResponseEntity.ok(serviceCenters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCenterResponseDTO> updateServiceCenter(
            @PathVariable Integer id, @Valid @RequestBody ServiceCenterDTO serviceCenterDTO) {
        ServiceCenterResponseDTO updatedServiceCenter = serviceCenterService.updateServiceCenter(id, serviceCenterDTO);
        return ResponseEntity.ok(updatedServiceCenter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCenter(@PathVariable Integer id) {
        serviceCenterService.deleteServiceCenter(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleServiceCenterStatus(
            @PathVariable Integer id, @RequestParam Boolean active) {
        serviceCenterService.toggleServiceCenterStatus(id, active);
        return ResponseEntity.noContent().build();
    }
}

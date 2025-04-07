package lk.carservice.demo.service;

import lk.carservice.demo.dto.ServiceCenterDTO;
import lk.carservice.demo.dto.ServiceCenterResponseDTO;

import java.util.List;

public interface ServiceCenterService {
    ServiceCenterResponseDTO registerServiceCenter(ServiceCenterDTO serviceCenterDTO);
    ServiceCenterResponseDTO createServiceCenter(ServiceCenterDTO serviceCenterDTO);
    ServiceCenterResponseDTO getServiceCenterById(Integer id);
    List<ServiceCenterResponseDTO> getAllServiceCenters();
    List<ServiceCenterResponseDTO> getActiveServiceCenters();
    ServiceCenterResponseDTO updateServiceCenter(Integer id, ServiceCenterDTO serviceCenterDTO);
    void deleteServiceCenter(Integer id);
    void toggleServiceCenterStatus(Integer id, Boolean status);
    List<ServiceCenterResponseDTO> searchServiceCenters(String query);
}

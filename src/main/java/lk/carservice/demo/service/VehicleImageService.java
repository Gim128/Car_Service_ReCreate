package lk.carservice.demo.service;

import lk.carservice.demo.dto.VehicleImageDTO;
import lk.carservice.demo.dto.VehicleImageResponseDTO;

import java.util.List;

public interface VehicleImageService {
    VehicleImageResponseDTO uploadImage(VehicleImageDTO imageDTO);
    VehicleImageResponseDTO getImageById(Integer imageId);
    List<VehicleImageResponseDTO> getImagesByCarId(Integer carId);
    VehicleImageResponseDTO setPrimaryImage(Integer imageId);
    void deleteImage(Integer imageId);
    void deleteAllImagesForCar(Integer carId);
    String getImageUrl(Integer imageId);
}

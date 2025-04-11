package lk.carservice.demo.service.impl;

import ch.qos.logback.classic.log4j.XMLLayout;
import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.VehicleImageDTO;
import lk.carservice.demo.dto.VehicleImageResponseDTO;
import lk.carservice.demo.entity.VehicleImage;
import lk.carservice.demo.exceptions.ImageProcessingException;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.VehicleImageRepository;
import lk.carservice.demo.service.VehicleImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VehicleImageServiceImpl implements VehicleImageService {

    private final VehicleImageRepository vehicleImageRepository;
    private final ModelMapper modelMapper;

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDirectory;

    @Autowired
    public VehicleImageServiceImpl(VehicleImageRepository vehicleImageRepository, ModelMapper modelMapper) {
        this.vehicleImageRepository = vehicleImageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public VehicleImageResponseDTO uploadImage(VehicleImageDTO imageDTO) {
        try {
            MultipartFile file = imageDTO.getImageFile();
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

            // Save file to filesystem
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save to database
            VehicleImage image = new VehicleImage();
            image.setCarId(imageDTO.getCarId());
            image.setContentType(file.getContentType());
            image.setFileName(uniqueFileName);
            image.setIsPrimary(imageDTO.getIsPrimary() != null && imageDTO.getIsPrimary());

            // If this is primary, unset any existing primary images
            if (image.getIsPrimary()) {
                vehicleImageRepository.findByCarIdAndIsPrimary(image.getCarId(), true)
                        .forEach(existingPrimary -> {
                            existingPrimary.setIsPrimary(false);
                            vehicleImageRepository.save(existingPrimary);
                        });
            }

            VehicleImage savedImage = vehicleImageRepository.save(image);
            return convertToResponseDTO(savedImage, filePath);
        } catch (IOException ex) {
            throw new ImageProcessingException("Failed to store image: " + ex.getMessage());
        }
    }

    private VehicleImageResponseDTO convertToResponseDTO(VehicleImage savedImage, Path filePath) {
        XMLLayout image = new XMLLayout();
        VehicleImageResponseDTO dto = modelMapper.map(image, VehicleImageResponseDTO.class);
        try {
            byte[] fileContent = Files.readAllBytes(filePath);
            String base64Image = Base64.getEncoder().encodeToString(fileContent);
            dto.setImageUrl("data:" + image.getContentType() + ";base64," + base64Image);
        } catch (IOException ex) {
            throw new ImageProcessingException("Failed to read image file: " + ex.getMessage());
        }
        return dto;
    }

    @Override
    public VehicleImageResponseDTO getImageById(Integer imageId) {
        VehicleImage image = vehicleImageRepository.findById(Long.valueOf(imageId))
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));

        Path filePath = Paths.get(uploadDirectory).resolve(image.getFileName());
        return convertToResponseDTO(image, filePath);
    }

    @Override
    public List<VehicleImageResponseDTO> getImagesByCarId(Integer carId) {
        return vehicleImageRepository.findByCarId(carId).stream()
                .map(image -> {
                    Path filePath = Paths.get(uploadDirectory).resolve(image.getFileName());
                    return convertToResponseDTO(image, filePath);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VehicleImageResponseDTO setPrimaryImage(Integer imageId) {
        VehicleImage image = vehicleImageRepository.findById(Long.valueOf(imageId))
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));

        // Unset any existing primary images
        vehicleImageRepository.findByCarIdAndIsPrimary(image.getCarId(), true)
                .forEach(existingPrimary -> {
                    existingPrimary.setIsPrimary(false);
                    vehicleImageRepository.save(existingPrimary);
                });

        // Set new primary image
        image.setIsPrimary(true);
        VehicleImage updatedImage = vehicleImageRepository.save(image);

        Path filePath = Paths.get(uploadDirectory).resolve(updatedImage.getFileName());
        return convertToResponseDTO(updatedImage, filePath);
    }

    @Override
    @Transactional
    public void deleteImage(Integer imageId) {
        VehicleImage image = vehicleImageRepository.findById(Long.valueOf(imageId))
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));

        try {
            // Delete from filesystem
            Path filePath = Paths.get(uploadDirectory).resolve(image.getFileName());
            Files.deleteIfExists(filePath);

            // Delete from database
            vehicleImageRepository.delete(image);

            // If this was primary, set another image as primary if available
            if (image.getIsPrimary()) {
                vehicleImageRepository.findByCarId(image.getCarId()).stream()
                        .findFirst()
                        .ifPresent(newPrimary -> {
                            newPrimary.setIsPrimary(true);
                            vehicleImageRepository.save(newPrimary);
                        });
            }
        } catch (IOException ex) {
            throw new ImageProcessingException("Failed to delete image file: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteAllImagesForCar(Integer carId) {
        List<VehicleImage> images = vehicleImageRepository.findByCarId(carId);
        images.forEach(image -> {
            try {
                Path filePath = Paths.get(uploadDirectory).resolve(image.getFileName());
                Files.deleteIfExists(filePath);
            } catch (IOException ex) {
                throw new ImageProcessingException("Failed to delete image file: " + ex.getMessage());
            }
        });
        vehicleImageRepository.deleteByCarId(carId);
    }

    @Override
    public String getImageUrl(Integer imageId) {
        VehicleImage image = vehicleImageRepository.findById(Long.valueOf(imageId))
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageId));
        return "/api/vehicle-images/" + imageId + "/content";
    }
}

package lk.carservice.demo.controller;

import lk.carservice.demo.dto.VehicleImageDTO;
import lk.carservice.demo.dto.VehicleImageResponseDTO;
import lk.carservice.demo.service.VehicleImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle-images")
public class VehicleImageController {

    private final VehicleImageService vehicleImageService;

    @Autowired
    public VehicleImageController(VehicleImageService vehicleImageService) {
        this.vehicleImageService = vehicleImageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VehicleImageResponseDTO> uploadImage(@ModelAttribute VehicleImageDTO imageDTO) {
        VehicleImageResponseDTO response = vehicleImageService.uploadImage(imageDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<VehicleImageResponseDTO> getImage(@PathVariable Integer imageId) {
        return ResponseEntity.ok(vehicleImageService.getImageById(imageId));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<VehicleImageResponseDTO>> getImagesByCard(@PathVariable Integer cardId) {
        return ResponseEntity.ok(vehicleImageService.getImagesByCarId(cardId));
    }

    @GetMapping("/{imageId}/content")
    public ResponseEntity<byte[]> getImageContent(@PathVariable Integer imageId) {
        VehicleImageResponseDTO image = vehicleImageService.getImageById(imageId);
        String[] parts = image.getImageUrl().split(",");
        byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(imageBytes);
    }

    @PutMapping("/{imageId}/primary")
    public ResponseEntity<VehicleImageResponseDTO> setPrimaryImage(@PathVariable Integer imageId) {
        return ResponseEntity.ok(vehicleImageService.setPrimaryImage(imageId));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer imageId) {
        vehicleImageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<Void> deleteAllImagesForCard(@PathVariable Integer cardId) {
        vehicleImageService.deleteAllImagesForCar(cardId);
        return ResponseEntity.noContent().build();
    }
}

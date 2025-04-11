package lk.carservice.demo.controller;

import jakarta.validation.Valid;
import lk.carservice.demo.dto.CarDTO;
import lk.carservice.demo.dto.CarResponseDTO;
import lk.carservice.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarResponseDTO> createCar(@Valid @RequestBody CarDTO carDTO) {
        CarResponseDTO createdCar = carService.createCar(carDTO);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDTO>> getAllCars(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear) {

        if (userId != null) {
            return ResponseEntity.ok(carService.getCarsByUser(userId));
        }
        return ResponseEntity.ok(carService.searchCars(make, model, minYear, maxYear));
    }

    @PutMapping("/{carId}")
    public ResponseEntity<CarResponseDTO> updateCar(
            @PathVariable Integer carId,
            @Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateCar(carId, carDTO));
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{carId}/activate")
    public ResponseEntity<CarResponseDTO> activateCar(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.activateCar(carId));
    }

    @PostMapping("/{carId}/deactivate")
    public ResponseEntity<CarResponseDTO> deactivateCar(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.deactivateCar(carId));
    }
}

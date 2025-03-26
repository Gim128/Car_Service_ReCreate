package lk.carservice.demo.controller;

import lk.carservice.demo.entity.Car;
import lk.carservice.demo.repository.CarRepository;
import lk.carservice.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Car> crearCar(@RequestBody Car carDetails) {
        Car car = carService.createCar(carDetails);
        return ResponseEntity.ok().body(car);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCarParts() {
        List<Car> carParts = carService.getAllCars();
        return ResponseEntity.ok(carParts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarPartById(@PathVariable Long id) {
        Car carPart = carService.getCarById(id);
        return ResponseEntity.ok(carPart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCarPart(@PathVariable Long id, @RequestBody Car carDetails) {
        Car updatedCarPart = carService.updateCar(id, carDetails);
        return ResponseEntity.ok(updatedCarPart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarPart(@PathVariable Long id) {
        carService.deleteCarPart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Car>> getCarPartsByCategory(@PathVariable String category) {
        List<Car> carParts = carService.getCarPartsByCategory(category);
        return ResponseEntity.ok(carParts);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Car>> getActiveCarParts() {
        List<Car> carParts = carService.getActiveCarParts();
        return ResponseEntity.ok(carParts);
    }
}

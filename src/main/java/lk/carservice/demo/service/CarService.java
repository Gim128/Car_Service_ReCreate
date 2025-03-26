package lk.carservice.demo.service;

import lk.carservice.demo.entity.Car;
import lk.carservice.demo.exceptions.CarNotFoundException;
import lk.carservice.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car createCar(Car car) {
        // Validation for registeredYear
        if (car.getRegisteredYear() == null ||
                car.getRegisteredYear().isAfter(Year.now())) {
            throw new IllegalArgumentException("Invalid registration year");
        }
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public Car updateCar(Long id, Car carDetails) {
        Car car = getCarById(id);

        car.setName(carDetails.getName());
        car .setDescription(carDetails.getDescription());
        car.setCategory(carDetails.getCategory());
        car.setManufacturer(carDetails.getManufacturer());
        car.setPrice(carDetails.getPrice());
        car.setRegisteredYear(carDetails.getRegisteredYear());
        car.setIsActive(carDetails.getIsActive());

        return carRepository.save(car);

    }

    public List<Car> getCarsByRegistrationYear(Year year) {
        return carRepository.findByRegisteredYear(year);
    }

    public List<Car> getCarsRegisteredBetween(Year start, Year end) {
        return carRepository.findByRegisteredYearBetween(start, end);
    }

    public void deleteCarPart(Long id) {
        Car car = getCarById(id);
        carRepository.delete(car);
    }

    public List<Car> getCarPartsByCategory(String category) {
        return carRepository.findByCategory(category);
    }

    public List<Car> getActiveCarParts() {
        return carRepository.findByIsActive(true);
    }


}

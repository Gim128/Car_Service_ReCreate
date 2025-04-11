package lk.carservice.demo.service;

import lk.carservice.demo.dto.CarDTO;
import lk.carservice.demo.dto.CarResponseDTO;

import java.util.List;

public interface CarService {
    CarResponseDTO createCar(CarDTO carDTO);
    CarResponseDTO getCarById(Integer carId);
    List<CarResponseDTO> getAllCars();
    List<CarResponseDTO> getCarsByUser(Integer userId);
    CarResponseDTO updateCar(Integer carId, CarDTO carDTO);
    void deleteCar(Integer carId);
    CarResponseDTO activateCar(Integer carId);
    CarResponseDTO deactivateCar(Integer carId);
    List<CarResponseDTO> searchCars(String make, String model, Integer minYear, Integer maxYear);
}

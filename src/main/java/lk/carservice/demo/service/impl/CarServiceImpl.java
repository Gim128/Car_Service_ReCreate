package lk.carservice.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.carservice.demo.dto.CarDTO;
import lk.carservice.demo.dto.CarResponseDTO;
import lk.carservice.demo.entity.Car;
import lk.carservice.demo.exceptions.DuplicateResourceException;
import lk.carservice.demo.exceptions.ResourceNotFoundException;
import lk.carservice.demo.repository.CarRepository;
import lk.carservice.demo.service.CarService;
import lk.carservice.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, UserService userService, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CarResponseDTO createCar(CarDTO carDTO) {
        if (carDTO.getLicensePlate() != null &&
                !carDTO.getLicensePlate().isEmpty() &&
                carRepository.existsByLicensePlate(carDTO.getLicensePlate())) {
            throw new DuplicateResourceException("License plate already exists");
        }

        // Check for duplicate VIN
        if (carDTO.getVin() != null &&
                !carDTO.getVin().isEmpty() &&
                carRepository.existsByVin(carDTO.getVin())) {
            throw new DuplicateResourceException("VIN already exists");
        }

        Car car = modelMapper.map(carDTO, Car.class);
        car.setIsActive(true);
        Car savedCar = carRepository.save(car);
        return enrichCarResponse(savedCar);
    }

    private CarResponseDTO enrichCarResponse(Car car) {
        CarResponseDTO response = modelMapper.map(car, CarResponseDTO.class);
        response.setOwnerName(userService.getUserNameById(car.getUserId()));
        return response;
    }

    @Override
    public CarResponseDTO getCarById(Integer carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        return enrichCarResponse(car);
    }

    @Override
    public List<CarResponseDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(this::enrichCarResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDTO> getCarsByUser(Integer userId) {
        return carRepository.findByUserId(userId)
                .stream()
                .map(this::enrichCarResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CarResponseDTO updateCar(Integer carId, CarDTO carDTO) {
        Car existingCar = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));

        // Check for duplicate license plate
        if (carDTO.getLicensePlate() != null &&
                !carDTO.getLicensePlate().equals(existingCar.getLicensePlate()) &&
                carRepository.existsByLicensePlate(carDTO.getLicensePlate())) {
            throw new DuplicateResourceException("License plate already exists");
        }

        // Check for duplicate VIN
        if (carDTO.getVin() != null &&
                !carDTO.getVin().equals(existingCar.getVin()) &&
                carRepository.existsByVin(carDTO.getVin())) {
            throw new DuplicateResourceException("VIN already exists");
        }

        modelMapper.map(carDTO, existingCar);
        existingCar.setCarId(carId);
        Car updatedCar = carRepository.save(existingCar);
        return enrichCarResponse(updatedCar);
    }

    @Override
    @Transactional
    public void deleteCar(Integer carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        carRepository.delete(car);
    }

    @Override
    @Transactional
    public CarResponseDTO activateCar(Integer carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        car.setIsActive(true);
        Car activatedCar = carRepository.save(car);
        return enrichCarResponse(activatedCar);
    }

    @Override
    @Transactional
    public CarResponseDTO deactivateCar(Integer carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        car.setIsActive(false);
        Car deactivatedCar = carRepository.save(car);
        return enrichCarResponse(deactivatedCar);
    }

    @Override
    public List<CarResponseDTO> searchCars(String make, String model, Integer minYear, Integer maxYear) {
        if (make != null && model != null) {
            return carRepository.findByMakeAndModel(make, model)
                    .stream()
                    .filter(car -> filterByYear(car, minYear, maxYear))
                    .map(this::enrichCarResponse)
                    .collect(Collectors.toList());
        } else if (make != null) {
            return carRepository.findByMake(make)
                    .stream()
                    .filter(car -> filterByYear(car, minYear, maxYear))
                    .map(this::enrichCarResponse)
                    .collect(Collectors.toList());
        } else if (minYear != null || maxYear != null) {
            Integer startYear = minYear != null ? minYear : 1900;
            Integer endYear = maxYear != null ? maxYear : 2100;
            return carRepository.findByYearBetween(startYear, endYear)
                    .stream()
                    .map(this::enrichCarResponse)
                    .collect(Collectors.toList());
        }
        return getAllCars();
    }

    private boolean filterByYear(Car car, Integer minYear, Integer maxYear) {
        if (minYear == null && maxYear == null) return true;
        if (minYear != null && car.getYear() < minYear) return false;
        if (maxYear != null && car.getYear() > maxYear) return false;
        return true;
    }
}

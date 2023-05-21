package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarService {

    private final CarRepository carRepository;

    public Optional<Car> create(Car car) {
        return carRepository.create(car);
    }

    public boolean update(Car car) {
        return carRepository.update(car);
    }

    public boolean delete(int carId) {
        return carRepository.delete(carId);
    }

    public List<Car> findAllOrderById() {
        return carRepository.findAllOrderById();
    }

    public Optional<Car> findById(int carId) {
        return carRepository.findById(carId);
    }
}

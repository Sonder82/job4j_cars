package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Optional<Car> create(Car car);

    boolean update(Car car);

    boolean delete(int carId);

    List<Car> findAllOrderById();

    Optional<Car> findById(int carId);
}

package ru.job4j.cars.service;

import ru.job4j.cars.model.ModelCar;

import java.util.List;
import java.util.Optional;

public interface ModelCarService {

    Optional<ModelCar> create(ModelCar modelCar);

    boolean update(ModelCar modelCar);

    boolean delete(int modelCarId);

    List<ModelCar> findAllOrderById();

    Optional<ModelCar> findById(int modelCarId);
}

package ru.job4j.cars.repository;

import ru.job4j.cars.model.ModelCar;

import java.util.List;
import java.util.Optional;

public interface ModelCarRepository {

    Optional<ModelCar> create(ModelCar modelCar);

    boolean update(ModelCar modelCar);

    boolean delete(int modelCarId);

    List<ModelCar> findAllOrderById();

    Optional<ModelCar> findById(int modelCarId);
}

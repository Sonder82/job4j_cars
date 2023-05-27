package ru.job4j.cars.service;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineService {

    Optional<Engine> create(Engine engine);

    boolean update(Engine engine);

    boolean delete(int engineId);

    List<Engine> findAllOrderById();

    Optional<Engine> findById(int engineId);
}

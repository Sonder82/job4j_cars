package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.ModelCar;
import ru.job4j.cars.repository.ModelCarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModelCarSimpleService implements ModelCarService {

    private final ModelCarRepository modelCarRepository;

    @Override
    public Optional<ModelCar> create(ModelCar modelCar) {
        return modelCarRepository.create(modelCar);
    }

    @Override
    public boolean update(ModelCar modelCar) {
        return modelCarRepository.update(modelCar);
    }

    @Override
    public boolean delete(int modelCarId) {
        return modelCarRepository.delete(modelCarId);
    }

    @Override
    public List<ModelCar> findAllOrderById() {
        return modelCarRepository.findAllOrderById();
    }

    @Override
    public Optional<ModelCar> findById(int modelCarId) {
        return modelCarRepository.findById(modelCarId);
    }
}

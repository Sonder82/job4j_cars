package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.ModelCar;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlModelCarRepository implements ModelCarRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<ModelCar> create(ModelCar modelCar) {
        crudRepository.run(session -> session.save(modelCar));
        return Optional.of(modelCar);
    }

    @Override
    public boolean update(ModelCar modelCar) {
        crudRepository.run(session -> session.merge(modelCar));
        return true;
    }

    @Override
    public boolean delete(int modelCarId) {
        crudRepository.run(
                "DELETE ModelCar WHERE id = :fId",
                Map.of("fId", modelCarId));
        return true;
    }

    @Override
    public List<ModelCar> findAllOrderById() {
        return crudRepository.query(
                "FROM ModelCar ORDER BY id", ModelCar.class);
    }

    @Override
    public Optional<ModelCar> findById(int modelCarId) {
        return crudRepository.optional(
                "FROM ModelCar  WHERE id = :fId", ModelCar.class,
                Map.of("fId", modelCarId));
    }
}

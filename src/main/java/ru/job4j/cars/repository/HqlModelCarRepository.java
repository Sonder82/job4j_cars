package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.ModelCar;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlModelCarRepository implements ModelCarRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HqlEngineRepository.class.getName());

    private final CrudRepository crudRepository;

    @Override
    public Optional<ModelCar> create(ModelCar modelCar) {
        Optional<ModelCar> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(modelCar));
            rsl = Optional.of(modelCar);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public boolean update(ModelCar modelCar) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(modelCar));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public boolean delete(int modelCarId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE ModelCar WHERE id = :fId",
                    Map.of("fId", modelCarId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
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
                Map.of("fId", modelCarId)
        );
    }
}

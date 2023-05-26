package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlTransmissionRepository implements TransmissionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HqlEngineRepository.class.getName());

    private final CrudRepository crudRepository;

    @Override
    public Optional<Transmission> create(Transmission transmission) {
        Optional<Transmission> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(transmission));
            rsl = Optional.of(transmission);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public boolean update(Transmission transmission) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(transmission));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public boolean delete(int transmissionId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE Tansmission WHERE id = :fId",
                    Map.of("fId", transmissionId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public List<Transmission> findAllOrderById() {
        return crudRepository.query(
                "FROM Transmission ORDER BY id", Transmission.class);
    }

    @Override
    public Optional<Transmission> findById(int transmissionId) {
        return crudRepository.optional(
                "FROM Transmission  WHERE id = :fId", Transmission.class,
                Map.of("fId", transmissionId)
        );
    }
}

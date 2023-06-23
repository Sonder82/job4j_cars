package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlTransmissionRepository implements TransmissionRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Transmission> create(Transmission transmission) {
        crudRepository.run(session -> session.save(transmission));
        return Optional.of(transmission);
    }

    @Override
    public boolean update(Transmission transmission) {
        crudRepository.run(session -> session.merge(transmission));
        return true;
    }

    @Override
    public boolean delete(int transmissionId) {
        crudRepository.run(
                "DELETE Tansmission WHERE id = :fId",
                Map.of("fId", transmissionId));
        return true;
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
                Map.of("fId", transmissionId));
    }
}

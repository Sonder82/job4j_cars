package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.History;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class HistoryRepository {

    private final CrudRepository crudRepository;


    public Optional<History> create(History history) {
        crudRepository.run(session -> session.persist(history));
        return Optional.of(history);
    }

    public boolean update(History history) {
        crudRepository.run(session -> session.merge(history));
        return true;
    }

    public boolean delete(int historyId) {
        crudRepository.run(
                "DELETE History WHERE id = :fId",
                Map.of("fId", historyId));
        return true;
    }


    public List<History> findAllOrderById() {
        return crudRepository.query(
                "FROM History ORDER BY id", History.class);
    }

    public Optional<History> findById(int historyId) {
        return crudRepository.optional(
                "FROM History  WHERE f.id = :fId", History.class,
                Map.of("fId", historyId));
    }
}

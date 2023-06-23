package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlBodyRepository implements BodyRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Body> create(Body body) {
        crudRepository.run(session -> session.save(body));
        return Optional.of(body);
    }

    @Override
    public boolean update(Body body) {
        crudRepository.run(session -> session.merge(body));
        return true;
    }

    @Override
    public boolean delete(int bodyId) {
        crudRepository.run(
                "DELETE Body WHERE id = :fId",
                Map.of("fId", bodyId));
        return true;
    }

    @Override
    public List<Body> findAllOrderById() {
        return crudRepository.query(
                "FROM Body ORDER BY id", Body.class);
    }

    @Override
    public Optional<Body> findById(int bodyId) {
        return crudRepository.optional(
                "FROM Body  WHERE id = :fId", Body.class,
                Map.of("fId", bodyId));
    }
}

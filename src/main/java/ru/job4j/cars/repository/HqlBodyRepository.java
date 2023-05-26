package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlBodyRepository implements BodyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HqlEngineRepository.class.getName());

    private final CrudRepository crudRepository;

    @Override
    public Optional<Body> create(Body body) {
        Optional<Body> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(body));
            rsl = Optional.of(body);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public boolean update(Body body) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(body));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public boolean delete(int bodyId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE Body WHERE id = :fId",
                    Map.of("fId", bodyId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
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
                Map.of("fId", bodyId)
        );
    }
}

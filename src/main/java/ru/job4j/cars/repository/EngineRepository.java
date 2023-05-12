package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EngineRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private static final Logger LOG = LoggerFactory.getLogger(EngineRepository.class.getName());

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param engine двигатель.
     * @return двигатель с id.
     */
    public Optional<Engine> create(Engine engine) {
        Optional<Engine> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(engine));
            rsl = Optional.of(engine);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Обновить в базе двигатель.
     *
     * @param engine двигатель.
     */
    public boolean update(Engine engine) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(engine));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;

    }

    /**
     * Удалить двигатель по id.
     *
     * @param engineId ID
     */
    public boolean delete(int engineId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "delete from Engine WHERE id = :fId",
                    Map.of("fId", engineId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;

    }

    /**
     * Список двигателей отсортированных по id.
     *
     * @return список двигателей.
     */
    public List<Engine> findAllOrderById() {
        return crudRepository.query(
                "FROM Engine ORDER BY id", Engine.class);
    }

    /**
     * Найти двигатель по ID
     *
     * @return двигатель.
     */
    public Optional<Engine> findById(int engineId) {
        return crudRepository.optional(
                "FROM Engine  WHERE id = :fId", Engine.class,
                Map.of("fId", engineId)
        );
    }
}

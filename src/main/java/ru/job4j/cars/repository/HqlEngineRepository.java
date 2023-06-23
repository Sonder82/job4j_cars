package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlEngineRepository implements EngineRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param engine двигатель.
     * @return двигатель с id.
     */
    @Override
    public Optional<Engine> create(Engine engine) {
        crudRepository.run(session -> session.save(engine));
        return Optional.of(engine);
    }

    /**
     * Обновить в базе двигатель.
     *
     * @param engine двигатель.
     */
    @Override
    public boolean update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
        return true;
    }

    /**
     * Удалить двигатель по id.
     *
     * @param engineId ID
     */
    @Override
    public boolean delete(int engineId) {
        crudRepository.run(
                "DELETE Engine WHERE id = :fId",
                Map.of("fId", engineId));
        return true;
    }

    /**
     * Список двигателей отсортированных по id.
     *
     * @return список двигателей.
     */
    @Override
    public List<Engine> findAllOrderById() {
        return crudRepository.query(
                "FROM Engine ORDER BY id", Engine.class);
    }

    /**
     * Найти двигатель по ID
     *
     * @return двигатель.
     */
    @Override
    public Optional<Engine> findById(int engineId) {
        return crudRepository.optional(
                "FROM Engine  WHERE id = :fId", Engine.class,
                Map.of("fId", engineId));
    }
}

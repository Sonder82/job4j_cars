package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlCarRepository implements CarRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param car автомобиль.
     * @return автомобиль с id.
     */
    @Override
    public Optional<Car> create(Car car) {
        crudRepository.run(session -> session.save(car));
        return Optional.of(car);
    }

    /**
     * Обновить в базе автомобиль.
     *
     * @param car автомобиль.
     */
    @Override
    public boolean update(Car car) {
        crudRepository.run(session -> session.merge(car));
        return true;
    }

    /**
     * Удалить автомобиль по id.
     *
     * @param carId ID
     */
    @Override
    public boolean delete(int carId) {
        crudRepository.run(
                "DELETE Car WHERE id = :fId",
                Map.of("fId", carId));
        return true;
    }

    /**
     * Список автомобилей отсортированных по id.
     *
     * @return список автомобилей.
     */
    @Override
    public List<Car> findAllOrderById() {
        return crudRepository.query(
                "FROM Car f JOIN FETCH f.engine ORDER BY f.id", Car.class);
    }

    /**
     * Найти автомобиль по ID
     *
     * @return автомобиль.
     */
    @Override
    public Optional<Car> findById(int carId) {
        return crudRepository.optional(
                "FROM Car f JOIN FETCH f.engine WHERE f.id = :fId", Car.class,
                Map.of("fId", carId));
    }
}

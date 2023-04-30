package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param car автомобиль.
     * @return автомобиль с id.
     */
    public Optional<Car> create(Car car) {
        Optional<Car> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(car));
            rsl = Optional.of(car);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Обновить в базе автомобиль.
     *
     * @param car автомобиль.
     */
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить автомобиль по id.
     *
     * @param carId ID
     */
    public void delete(int carId) {
        crudRepository.run(
                "delete from Car WHERE id = :fId",
                Map.of("fId", carId)
        );
    }

    /**
     * Список автомобилей отсортированных по id.
     *
     * @return список автомобилей.
     */
    public List<Car> findAllOrderById() {
        return crudRepository.query(
                "FROM Car ORDER BY id", Car.class);
    }

    /**
     * Найти автомобиль по ID
     *
     * @return автомобиль.
     */
    public Optional<Car> findById(int carId) {
        return crudRepository.optional(
                "FROM Car  WHERE f.id = :fId", Car.class,
                Map.of("fId", carId)
        );
    }
}

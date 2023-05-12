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
            crudRepository.run(session -> session.save(car));
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
    public boolean update(Car car) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(car));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Удалить автомобиль по id.
     *
     * @param carId ID
     */
    public boolean delete(int carId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "delete from Car WHERE id = :fId",
                    Map.of("fId", carId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
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
                "FROM Car f JOIN FETCH f.owners WHERE f.id = :fId", Car.class,
                Map.of("fId", carId)
        );
    }
}

package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PhotoRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private final CrudRepository crudRepository;

    public Optional<Photo> create(Photo photo) {
        Optional<Photo> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(photo));
            rsl = Optional.of(photo);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    public boolean update(Photo photo) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(photo));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    public boolean delete(int photoId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "delete from Photo WHERE id = :fId",
                    Map.of("fId", photoId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    public List<Photo> findAllOrderById() {
        return crudRepository.query(
                "FROM Photo ORDER BY id", Photo.class);
    }

    /**
     * Найти автомобиль по ID
     *
     * @return автомобиль.
     */
    public Optional<Photo> findById(int photoId) {
        return crudRepository.optional(
                "FROM Photo WHERE id = :fId", Photo.class,
                Map.of("fId", photoId)
        );
    }
}

package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlPhotoRepository implements PhotoRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HqlCarRepository.class.getName());

    private final CrudRepository crudRepository;

    @Override
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

    @Override
    public boolean delete(int photoId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE Photo WHERE id = :fId",
                    Map.of("fId", photoId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Найти автомобиль по ID
     *
     * @return автомобиль.
     */
    @Override
    public Optional<Photo> findById(int photoId) {
        return crudRepository.optional(
                "FROM Photo WHERE id = :fId", Photo.class,
                Map.of("fId", photoId)
        );
    }
}

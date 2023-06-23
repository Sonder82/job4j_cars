package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlPhotoRepository implements PhotoRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Photo> create(Photo photo) {
        crudRepository.run(session -> session.save(photo));
        return Optional.of(photo);
    }

    @Override
    public boolean delete(int photoId) {
            crudRepository.run(
                    "DELETE Photo WHERE id = :fId",
                    Map.of("fId", photoId));
        return true;
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
                Map.of("fId", photoId));
    }
}

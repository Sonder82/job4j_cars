package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private final CrudRepository crudRepository;

    public Optional<Post> create(Post post) {
        Optional<Post> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(post));
            rsl = Optional.of(post);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    public boolean update(Post post) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(post));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    public boolean delete(int postId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "delete from Post WHERE id = :fId",
                    Map.of("fId", postId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    public List<Post> findAllOrderById() {
        return crudRepository.query(
                "FROM Post ORDER BY id", Post.class);
    }

    /**
     * Найти автомобиль по ID
     *
     * @return автомобиль.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "FROM Post WHERE id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    public List<Post> findAllPostAtLastDay() {
        var dateNow = LocalDateTime.now();
        var date = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "FROM Post WHERE created BETWEEN :fDate AND :fDateNow", Post.class,
                Map.of("fDate", date, "fDateNow", dateNow));
    }

    public List<Post> findAllPostWithPhoto() {
        return crudRepository.query(
                "FROM Post WHERE photo_id IS NOT NULL", Post.class);
    }

    public List<Post> findAllPostWithModel(String name) {
        return crudRepository.query(
                "FROM Post WHERE car_id IN (SELECT id FROM Car WHERE name = :fName)", Post.class,
                Map.of("fName", name));
    }
}

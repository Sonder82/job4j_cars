package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
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
public class HqlPostRepository implements PostRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HqlCarRepository.class.getName());

    private final CrudRepository crudRepository;

    @Override
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

    @Override
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

    @Override
    public boolean delete(int postId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE Post WHERE id = :fId",
                    Map.of("fId", postId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public boolean setSold(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "Update Post SET sold = :fSold  WHERE id = :fId",
                    Map.of("fId", id, "fSold", true)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public List<Post> findAllOrderById() {
        return crudRepository.query(
                "FROM Post ORDER BY id", Post.class);
    }

    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "FROM Post WHERE id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    @Override
    public List<Post> findAllPostAtLastDay() {
        var dateNow = LocalDateTime.now();
        var date = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "FROM Post AS p  "
                        + "WHERE p.created BETWEEN :fDate AND :fDateNow", Post.class,
                Map.of("fDate", date, "fDateNow", dateNow));
    }

    @Override
    public List<Post> findAllPostWithPhoto() {
        return crudRepository.query(
                "FROM Post p JOIN FETCH p.photo WHERE p.photo.id IS NOT NULL", Post.class);
    }

    @Override
    public List<Post> findAllPostWithModel(String name) {
        return crudRepository.query(
                "FROM Post f JOIN FETCH f.car WHERE f.car.name = :fName", Post.class,
                Map.of("fName", name));
    }
}

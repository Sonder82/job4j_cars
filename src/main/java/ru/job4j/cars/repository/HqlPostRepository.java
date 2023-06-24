package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HqlPostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Post> create(Post post) {
        crudRepository.run(session -> session.save(post));
        return Optional.of(post);
    }

    @Override
    public boolean update(Post post) {
        crudRepository.run(session -> session.merge(post));
        return true;
    }

    @Override
    public boolean delete(int postId) {
        crudRepository.run(
                "DELETE Post WHERE id = :fId",
                Map.of("fId", postId));
        return true;
    }

    @Override
    public boolean setSold(int id) {
        crudRepository.run(
                "Update Post SET sold = :fSold  WHERE id = :fId",
                Map.of("fId", id, "fSold", true));
        return true;
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
                Map.of("fId", postId));
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
                "FROM Post p  WHERE p.photo IS NOT NULL", Post.class);
    }

    @Override
    public List<Post> findAllPostWithModel(String name) {
        return crudRepository.query(
                "FROM Post f JOIN FETCH f.car WHERE f.car.name = :fName", Post.class,
                Map.of("fName", name));
    }
}

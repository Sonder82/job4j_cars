package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    public List<Post> findAllPostAtLastDay() {
        var date = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "FROM Post WHERE created => :fDate", Post.class,
                Map.of("fDate", date));
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

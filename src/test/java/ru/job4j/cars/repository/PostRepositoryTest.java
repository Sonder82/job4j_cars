package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class PostRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final PostRepository postRepository = new PostRepository(crudRepository);

    private final UserRepository userRepository = new UserRepository(crudRepository);

    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    private final CarRepository carRepository = new CarRepository(crudRepository);

    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);

    @BeforeEach
    public void wipeTable() {
        Session session = sf.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.createQuery(
                    "DELETE FROM User").executeUpdate();
            session.createQuery(
                    "DELETE FROM Photo").executeUpdate();
            session.createQuery(
                    "DELETE FROM Post").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Test
    public void whenFindAllPostAtLastDay() {
        Engine engine = new Engine();
        engine.setName("diesel");
        engineRepository.create(engine);
        Car car = new Car();
        car.setName("model");
        car.setEngine(engine);
        carRepository.create(car);
        Photo photo = new Photo();
        photo.setName("name");
        photo.setPath("path");
        photoRepository.create(photo);
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setPhoto(photo);
        postRepository.create(post);
        assertThat(postRepository.findAllPostAtLastDay()).hasSize(1)
                .contains(post);
    }
}
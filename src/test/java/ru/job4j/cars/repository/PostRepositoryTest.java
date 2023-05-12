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
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class PostRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final CarRepository carRepository = new CarRepository(crudRepository);

    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    private final UserRepository userRepository = new UserRepository(crudRepository);

    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

    private final PostRepository postRepository = new PostRepository(crudRepository);

    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);

    @BeforeEach
    public void wipeTable() {
        Session session = sf.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.createQuery(
                    "DELETE FROM PriceHistory").executeUpdate();
            session.createQuery(
                    "DELETE FROM Post").executeUpdate();
            session.createQuery(
                    "DELETE FROM Photo").executeUpdate();
            session.createQuery(
                    "DELETE FROM Car").executeUpdate();
            session.createQuery(
                    "DELETE FROM Owner").executeUpdate();
            session.createQuery(
                    "DELETE FROM User").executeUpdate();
            session.createQuery(
                    "DELETE FROM Engine").executeUpdate();
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
        var engine = engineRepository.create(new Engine(1, "diesel"));
        var user = userRepository.create(new User(1, "test", "password"));
        var owner = ownerRepository.create(new Owner(1, "name", user));
        var car = carRepository.create(new Car(1, "model", engine.get(), Set.of(owner.get()))).get();
        var photo1 = photoRepository.create(new Photo(1, "name", "path")).get();
        var photo2 = photoRepository.create(new Photo(2, "name", "path2")).get();
        var post1 = postRepository.create(
                new Post(1, "test", LocalDateTime.now().minusWeeks(1),
                        user, car, List.of(new PriceHistory()), photo1, List.of(user))
        );
        var post2 = postRepository.create(
                new Post(2, "test", LocalDateTime.now(),
                        user, car, List.of(new PriceHistory()), photo2, List.of(user))
        );
        assertThat(postRepository.findAllPostAtLastDay()).hasSize(1)
                .contains(post2.get())
                .doesNotContain(post1.get());
    }

    @Test
    public void whenFindAllPostWithModel() {
        var engine = engineRepository.create(new Engine(1, "diesel"));
        var user = userRepository.create(new User(1, "test", "password"));
        var owner = ownerRepository.create(new Owner(1, "name", user));
        var car = carRepository.create(new Car(1, "model", engine.get(), Set.of(owner.get()))).get();
        var photo1 = photoRepository.create(new Photo(1, "name", "path")).get();
        var post1 = postRepository.create(
                new Post(1, "test", LocalDateTime.now().minusWeeks(1),
                        user, car, List.of(new PriceHistory()), photo1, List.of(user))
        );
        assertThat(postRepository.findAllPostWithModel("model")).hasSize(1);
    }

    @Test
    public void whenFindAllPostWithPhoto() {
        var engine = engineRepository.create(new Engine(1, "diesel"));
        var user = userRepository.create(new User(1, "test", "password"));
        var owner = ownerRepository.create(new Owner(1, "name", user));
        var car = carRepository.create(new Car(1, "model", engine.get(), Set.of(owner.get()))).get();
        var photo1 = photoRepository.create(new Photo(1, "name", "path")).get();
        var post1 = postRepository.create(
                new Post(1, "test", LocalDateTime.now().minusWeeks(1),
                        user, car, List.of(new PriceHistory()), photo1, List.of(user))
        );
        assertThat(postRepository.findAllPostWithPhoto()).hasSize(1);
    }
}
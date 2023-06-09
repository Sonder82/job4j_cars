package ru.job4j.cars.repository;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostRepositoryTest {

    private final SessionFactory sf = new MetadataSources(
            new StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final PostRepository postRepository = new HqlPostRepository(crudRepository);

    private final CarRepository carRepository = new HqlCarRepository(crudRepository);

    private final EngineRepository engineRepository = new HqlEngineRepository(crudRepository);

    private final PhotoRepository photoRepository = new HqlPhotoRepository(crudRepository);

    private final Post post = new Post();

    private final Car car = new Car();

    @BeforeEach
    public void initForPost() {
        engineRepository.create(new Engine(1, "test"));
        var engine = engineRepository.findAllOrderById().get(0);
        car.setName("model");
        car.setEngine(engine);
        carRepository.create(car);
        var carRsl = carRepository.findAllOrderById().get(0);
        photoRepository.create(new Photo(1, "test", "test"));
        var photo = photoRepository.findById(1);
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setCar(carRsl);
        post.setPhoto(photo.get());
        postRepository.create(post);
    }

    @AfterEach
    public void wipeTable() {
        var session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Post").executeUpdate();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM Photo").executeUpdate();
        session.createQuery("DELETE FROM Engine").executeUpdate();
        session.getTransaction();
        session.close();
    }

    @Test
    public void whenAddNewPostThenRepoHasSamePost() {

        assertThat(postRepository.findById(post.getId()).get()).isEqualTo(post);
    }

    @Test
    public void whenFindAllForTheLastDay() {

        assertThat(postRepository.findAllPostAtLastDay()).isEqualTo(List.of(post));
    }

    @Test
    public void whenFindWithPhoto() {

        assertThat(postRepository.findAllPostWithPhoto()).isEqualTo(List.of(post));
    }

    @Test
    public void whenFindByModel() {

        assertThat(postRepository.findAllPostWithModel(car.getName())).isEqualTo(List.of(post));
    }
}
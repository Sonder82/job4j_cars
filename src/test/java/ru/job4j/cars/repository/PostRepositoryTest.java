package ru.job4j.cars.repository;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private final ModelCarRepository modelCarRepository = new HqlModelCarRepository(crudRepository);

    private final BodyRepository bodyRepository = new HqlBodyRepository(crudRepository);

    private final TransmissionRepository transmissionRepository = new HqlTransmissionRepository(crudRepository);

    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

    private final UserRepository userRepository = new HqlUserRepository(crudRepository);

    private final Post post = new Post();

    private final Car car = new Car();

    @BeforeEach
    public void initForPost() {
        createFile();
        engineRepository.create(new Engine(1, "test"));
        modelCarRepository.create(new ModelCar(1, "modelCarTest"));
        bodyRepository.create(new Body(1, "bodyTest"));
        transmissionRepository.create(new Transmission(1, "transmissionTest"));
        var engine = engineRepository.findAllOrderById().get(0);
        var modelCar = modelCarRepository.findAllOrderById().get(0);
        var body = bodyRepository.findAllOrderById().get(0);
        var transmission = transmissionRepository.findAllOrderById().get(0);
        car.setName("model");
        car.setEngine(engine);
        car.setModelCar(modelCar);
        car.setBody(body);
        car.setTransmission(transmission);
        carRepository.create(car);
        var carRsl = carRepository.findAllOrderById().get(0);
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setCar(carRsl);
        post.setSold(false);
        post.setPrice(1);
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
        session.createQuery("DELETE FROM ModelCar").executeUpdate();
        session.createQuery("DELETE FROM Body").executeUpdate();
        session.createQuery("DELETE FROM Transmission").executeUpdate();
        session.getTransaction();
        session.close();
    }

    private void createFile() {
        var photo = new Photo();
        photo.setPath("path");
        photo.setName("name");
        photoRepository.create(photo);
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
    public void whenFindByModel() {

        assertThat(postRepository.findAllPostWithModel(car.getName())).isEqualTo(List.of(post));
    }
}
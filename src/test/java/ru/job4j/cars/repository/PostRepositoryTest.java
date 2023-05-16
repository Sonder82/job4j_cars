package ru.job4j.cars.repository;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class PostRepositoryTest {

    private final SessionFactory sf = new MetadataSources(
            new StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final PhotoRepository photoRepository = new PhotoRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        var session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Post").executeUpdate();
        session.getTransaction();
        session.close();
    }

    @Test
    public void whenAddNewPostThenRepoHasSamePost() {
        var engine = engineRepository.findAllOrderById().get(0);
        var car = carRepository.findAllOrderById().get(0);
        var photo = photoRepository.findAllOrderById().get(0);
        var post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setPhoto(photo);
        postRepository.create(post);
        assertThat(postRepository.findById(post.getId()).get()).isEqualTo(post);
    }
}
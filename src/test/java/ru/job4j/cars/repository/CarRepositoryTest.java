package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class CarRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final CarRepository carRepository = new CarRepository(crudRepository);

    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    private final UserRepository userRepository = new UserRepository(crudRepository);

    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

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
    public void whenAddNewCarThenHasSameCar() {
        var engine = engineRepository.create(new Engine(1, "diesel"));
        var user = userRepository.create(new User(1, "test", "password"));
        var owner = ownerRepository.create(new Owner(1, "name", user));
        var car = carRepository.create(new Car(1, "model", engine.get(), Set.of(owner.get())));
        assertThat(carRepository.findById(car.get().getId()).get()).isEqualTo(car.get());
    }

    @Test
    public void whenAddNewCarThenUpdateName() {
        var engine = engineRepository.create(new Engine(1, "diesel"));
        var user = userRepository.create(new User(1, "test", "password"));
        var owner = ownerRepository.create(new Owner(1, "name", user));
        var car1 = carRepository.create(new Car(1, "model", engine.get(), Set.of(owner.get()))).get();
        car1.setName("newModel");
        carRepository.update(car1);
        assertThat(carRepository.findById(car1.getId()).get().getName()).isEqualTo("newModel");
    }

    @Test
    public void whenDeleteCarThenCheckContains() {
        var engine = engineRepository.create(new Engine(1, "diesel"));
        var user = userRepository.create(new User(1, "test", "password"));
        var owner = ownerRepository.create(new Owner(1, "name", user));
        var car1 = carRepository.create(new Car(1, "model", engine.get(), Set.of(owner.get()))).get();
        var car2 = carRepository.create(new Car(2, "model", engine.get(), Set.of(owner.get()))).get();
        carRepository.delete(car1.getId());
        assertThat(carRepository.findAllOrderById()).hasSize(1)
                .contains(car2)
                .doesNotContain(car1);
    }
}
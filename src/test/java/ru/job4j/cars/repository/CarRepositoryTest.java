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

import static org.assertj.core.api.Assertions.*;

public class CarRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final CarRepository carRepository = new CarRepository(crudRepository);

    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @BeforeEach
    public void wipeTable() {
        Session session = sf.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.createQuery(
                    "DELETE FROM Car").executeUpdate();
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
        var engine = engineRepository.create(new Engine(1, "diesel")).get();
        Car car = new Car();
        car.setName("model");
        car.setEngine(engine);
        carRepository.create(car);
        assertThat(carRepository.findById(car.getId()).get()).isEqualTo(car);
    }

    @Test
    public void whenAddNewCarThenUpdateName() {
        var engine = engineRepository.create(new Engine(1, "diesel")).get();
        Car car = new Car();
        car.setName("model");
        car.setEngine(engine);
        carRepository.create(car);
        car.setName("newModel");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).get().getName()).isEqualTo("newModel");
    }

    @Test
    public void whenDeleteCarThenCheckContains() {
        var engine1 = engineRepository.create(new Engine(1, "diesel")).get();
        var engine2 = engineRepository.create(new Engine(1, "benzin")).get();
        Car car1 = new Car();
        car1.setName("model");
        car1.setEngine(engine1);
        Car car2 = new Car();
        car2.setName("model");
        car2.setEngine(engine2);
        var rsl1 = carRepository.create(car1);
        var rsl2 = carRepository.create(car2);
        carRepository.delete(car1.getId());
        assertThat(carRepository.findAllOrderById()).hasSize(1)
                .contains(car2)
                .doesNotContain(car1);
    }
}
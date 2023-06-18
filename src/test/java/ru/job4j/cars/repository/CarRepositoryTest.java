package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import static org.assertj.core.api.Assertions.*;

public class CarRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final CarRepository carRepository = new HqlCarRepository(crudRepository);

    private final EngineRepository engineRepository = new HqlEngineRepository(crudRepository);

    private final ModelCarRepository modelCarRepository = new HqlModelCarRepository(crudRepository);

    private final BodyRepository bodyRepository = new HqlBodyRepository(crudRepository);

    private final TransmissionRepository transmissionRepository = new HqlTransmissionRepository(crudRepository);

    private final Car car = new Car();

    @BeforeEach
    public void initForCar() {
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
    }

    @AfterEach
    public void wipeTable() {
        Session session = sf.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.createQuery("DELETE FROM Post").executeUpdate();
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.createQuery("DELETE FROM Engine").executeUpdate();
            session.createQuery("DELETE FROM ModelCar").executeUpdate();
            session.createQuery("DELETE FROM Body").executeUpdate();
            session.createQuery("DELETE FROM Transmission").executeUpdate();
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
        assertThat(carRepository.findById(car.getId()).get()).isEqualTo(car);
    }

    @Test
    public void whenAddNewCarThenUpdateName() {
        car.setName("newModel");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).get().getName()).isEqualTo("newModel");
    }

    @Test
    public void whenDeleteCarThenCheckContains() {
        carRepository.delete(car.getId());
        assertThat(carRepository.findAllOrderById()).doesNotContain(car);
    }
}
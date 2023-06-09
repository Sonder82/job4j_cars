package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import static org.assertj.core.api.Assertions.*;

public class EngineRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final EngineRepository engineRepository = new HqlEngineRepository(crudRepository);

    @BeforeEach
    public void wipeTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE FROM Engine").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
   public void whenCreateEngineThenGetIt() {
        Engine engine = engineRepository.create(new Engine(1, "test")).get();
        assertThat(engineRepository.findById(engine.getId()).get()).isEqualTo(engine);
    }

    @Test
    public void whenAddNewEngineThenUpdateName() {
        Engine engine1 = engineRepository.create(new Engine(1, "test")).get();
        engine1.setName("newName");
        engineRepository.update(engine1);
        assertThat(engineRepository.findById(engine1.getId()).get().getName()).isEqualTo("newName");
    }

    @Test
    public void whenDeleteEngineThenCheckContains() {
        Engine engine1 = engineRepository.create(new Engine(1, "test")).get();
        engineRepository.delete(engine1.getId());
        assertThat(engineRepository.findAllOrderById()).doesNotContain(engine1);
    }
}
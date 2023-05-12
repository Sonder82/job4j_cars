package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import static org.assertj.core.api.Assertions.*;

public class UserRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final UserRepository userRepository = new UserRepository(crudRepository);

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
    public void whenCreateUserThenGetHim() {
        User user = userRepository.create(new User(1, "login", "password"));
        assertThat(userRepository.findById(user.getId()).get()).isEqualTo(user);
    }

    @Test
    public void whenAddNewUserThenUpdatePassword() {
        User user = userRepository.create(new User(1, "login2", "password"));
        user.setPassword("newPassword");
        userRepository.update(user);
        assertThat(userRepository.findById(user.getId()).get().getPassword()).isEqualTo("newPassword");
    }

    @Test
    public void whenDeleteUserThenCheckContains() {
        User user1 = userRepository.create(new User(1, "login3", "password"));
        User user2 = userRepository.create(new User(2, "login4", "password"));
        userRepository.delete(user1.getId());
        assertThat(userRepository.findAllOrderById()).hasSize(1)
                .contains(user2)
                .doesNotContain(user1);
    }
}
package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        crudRepository.run(session -> session.save(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public boolean update(User user) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(user));
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public boolean delete(int userId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "delete from User WHERE id = :fId",
                    Map.of("fId", userId)
            );
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User ORDER BY id asc", User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
       return crudRepository.optional(
                    "FROM User WHERE id = :fId", User.class,
               Map.of("fId", userId));

    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
       return crudRepository.query(
                    "FROM User WHERE login LIKE :fLogin", User.class,
               Map.of("fKey", "%" + key + "%"));

    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
       return crudRepository.optional(
                    "FROM User WHERE login = :fLogin", User.class,
                Map.of("fLogin", login));
    }
}

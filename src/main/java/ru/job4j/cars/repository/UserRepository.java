package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.time.ZoneId;
import java.util.*;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HqlCarRepository.class.getName());

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public Optional<User> create(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(user));
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
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
            rsl = true;
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
                    "DELETE User WHERE id = :fId",
                    Map.of("fId", userId)
            );
            rsl = true;
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

    public List<TimeZone> listZone() {
        List<TimeZone> zones = new ArrayList<>();
        for (String timeId : ZoneId.getAvailableZoneIds()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }
}

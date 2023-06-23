package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.time.ZoneId;
import java.util.*;

@Repository
@AllArgsConstructor
public class HqlUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> create(User user) {
        crudRepository.run(session -> session.save(user));
        return Optional.of(user);
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public boolean update(User user) {
        crudRepository.run(session -> session.merge(user));
        return true;
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public boolean delete(int userId) {
        crudRepository.run(
                "DELETE User WHERE id = :fId",
                Map.of("fId", userId));
        return true;
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User ORDER BY id asc", User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
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
    @Override
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
    @Override
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

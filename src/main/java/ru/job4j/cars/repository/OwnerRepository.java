package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerRepository.class.getName());

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param owner собственник.
     * @return собственник с id.
     */
    public Optional<Owner> create(Owner owner) {
        Optional<Owner> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.save(owner));
            rsl = Optional.of(owner);
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Обновить в базе владельца.
     *
     * @param owner владелец.
     */
    public boolean update(Owner owner) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(owner));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Удалить владельца по id.
     *
     * @param ownerId ID
     */
    public boolean delete(int ownerId) {
        boolean rsl = false;
        try {
            crudRepository.run(
                    "DELETE Owner WHERE id = :fId",
                    Map.of("fId", ownerId)
            );
            rsl = true;
        } catch (Exception e) {
            LOG.error("Error message: " + e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Список владельцев отсортированных по id.
     *
     * @return список владельцев.
     */
    public List<Owner> findAllOrderById() {
        return crudRepository.query(
                "FROM Owner ORDER BY id", Owner.class);
    }

    /**
     * Найти владельца по ID
     *
     * @return владельца.
     */
    public Optional<Owner> findById(int ownerId) {
        return crudRepository.optional(
                "FROM Owner  WHERE f.id = :fId", Owner.class,
                Map.of("fId", ownerId)
        );
    }
}

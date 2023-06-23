package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param owner собственник.
     * @return собственник с id.
     */
    public Optional<Owner> create(Owner owner) {
        crudRepository.run(session -> session.save(owner));
        return Optional.of(owner);
    }

    /**
     * Обновить в базе владельца.
     *
     * @param owner владелец.
     */
    public boolean update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
        return true;
    }

    /**
     * Удалить владельца по id.
     *
     * @param ownerId ID
     */
    public boolean delete(int ownerId) {
        crudRepository.run(
                "DELETE Owner WHERE id = :fId",
                Map.of("fId", ownerId));
        return true;
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
                Map.of("fId", ownerId));
    }
}

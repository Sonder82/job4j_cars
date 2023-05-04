package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.History;

import java.util.List;
import java.util.Map;
import java.util.Optional;


    @Repository
    @AllArgsConstructor
    public class HistoryRepository {

        private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();

        private static final Logger LOG = LoggerFactory.getLogger(ru.job4j.cars.repository.HistoryRepository.class.getName());

        private final CrudRepository crudRepository;


        public Optional<History> create(History history) {
            Optional<History> rsl = Optional.empty();
            try {
                crudRepository.run(session -> session.persist(history));
                rsl = Optional.of(history);
            } catch (Exception e) {
                LOG.error("Error message: " + e.getMessage(), e);
            }
            return rsl;
        }

        public void update(History history) {
            crudRepository.run(session -> session.merge(history));
        }

        public void delete(int historyId) {
            crudRepository.run(
                    "delete from History WHERE id = :fId",
                    Map.of("fId", historyId)
            );
        }


        public List<History> findAllOrderById() {
            return crudRepository.query(
                    "FROM History ORDER BY id", History.class);
        }

        public Optional<History> findById(int historyId) {
            return crudRepository.optional(
                    "FROM History  WHERE f.id = :fId", History.class,
                    Map.of("fId", historyId)
            );
        }
}

package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
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

        public boolean update(History history) {
            boolean rsl = false;
            try {
                crudRepository.run(session -> session.merge(history));
                rsl = true;
            } catch (Exception e) {
                LOG.error("Error message: " + e.getMessage(), e);
            }
            return rsl;
        }

        public boolean delete(int historyId) {
            boolean rsl = false;
            try {
                crudRepository.run(
                        "DELETE History WHERE id = :fId",
                        Map.of("fId", historyId)
                );
                rsl = true;
            } catch (Exception e) {
                LOG.error("Error message: " + e.getMessage(), e);
            }
            return rsl;
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

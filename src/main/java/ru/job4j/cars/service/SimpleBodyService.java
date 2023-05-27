package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.BodyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBodyService implements BodyService {

    private final BodyRepository bodyRepository;

    @Override
    public Optional<Body> create(Body body) {
        return bodyRepository.create(body);
    }

    @Override
    public boolean update(Body body) {
        return bodyRepository.update(body);
    }

    @Override
    public boolean delete(int bodyId) {
        return bodyRepository.delete(bodyId);
    }

    @Override
    public List<Body> findAllOrderById() {
        return bodyRepository.findAllOrderById();
    }

    @Override
    public Optional<Body> findById(int bodyId) {
        return bodyRepository.findById(bodyId);
    }
}

package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Transmission;
import ru.job4j.cars.repository.TransmissionRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransmissionSimpleService implements TransmissionService {

    private final TransmissionRepository transmissionRepository;

    @Override
    public Optional<Transmission> create(Transmission transmission) {
        return Optional.empty();
    }

    @Override
    public boolean update(Transmission transmission) {
        return false;
    }

    @Override
    public boolean delete(int transmissionId) {
        return false;
    }

    @Override
    public List<Transmission> findAllOrderById() {
        return null;
    }

    @Override
    public Optional<Transmission> findById(int transmissionId) {
        return Optional.empty();
    }
}

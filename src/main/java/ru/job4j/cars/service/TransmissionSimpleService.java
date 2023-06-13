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
        return transmissionRepository.create(transmission);
    }

    @Override
    public boolean update(Transmission transmission) {
        return transmissionRepository.update(transmission);
    }

    @Override
    public boolean delete(int transmissionId) {
        return transmissionRepository.delete(transmissionId);
    }

    @Override
    public List<Transmission> findAllOrderById() {
        return transmissionRepository.findAllOrderById();
    }

    @Override
    public Optional<Transmission> findById(int transmissionId) {
        return transmissionRepository.findById(transmissionId);
    }
}

package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class SimpleUserService {

    private final UserRepository userRepository;

    public Optional<User> create(User user) {
        return userRepository.create(user);
    }

    public boolean update(User user) {
        return userRepository.update(user);
    }

    public boolean delete(int userId) {
        return userRepository.delete(userId);
    }

    public List<User> findAllOrderById() {
        return userRepository.findAllOrderById();
    }

    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);
    }

    public List<User> findByLikeLogin(String key) {
        return userRepository.findByLikeLogin(key);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<TimeZone> listZone() {
        return userRepository.listZone();
    }
}

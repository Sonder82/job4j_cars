package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService {

    private final PostRepository postRepository;

    public Optional<Post> create(Post post) {
        return postRepository.create(post);
    }

    public boolean update(Post post) {
        return postRepository.update(post);
    }

    public boolean delete(int postId) {
        return postRepository.delete(postId);
    }

    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    public Optional<Post> findById(int postId) {
        return postRepository.findById(postId);
    }

    public List<Post> findAllPostAtLastDay() {
        return postRepository.findAllPostAtLastDay();
    }

    public List<Post> findAllPostWithPhoto() {
        return postRepository.findAllPostWithPhoto();
    }

    public List<Post> findAllPostWithModel(String name) {
        return postRepository.findAllPostWithModel(name);
    }
}

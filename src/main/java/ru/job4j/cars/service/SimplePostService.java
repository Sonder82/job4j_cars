package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public Optional<Post> create(Post post) {
        return postRepository.create(post);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean delete(int postId) {
        return postRepository.delete(postId);
    }

    @Override
    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    @Override
    public Optional<Post> findById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> findAllPostAtLastDay() {
        return postRepository.findAllPostAtLastDay();
    }

    @Override
    public List<Post> findAllPostWithPhoto() {
        return postRepository.findAllPostWithPhoto();
    }

    @Override
    public List<Post> findAllPostWithModel(String name) {
        return postRepository.findAllPostWithModel(name);
    }
}

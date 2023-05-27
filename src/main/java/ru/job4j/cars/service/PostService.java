package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> create(Post post);

    boolean update(Post post);

    boolean delete(int postId);

    List<Post> findAllOrderById();

    Optional<Post> findById(int postId);

    List<Post> findAllPostAtLastDay();

    List<Post> findAllPostWithPhoto();

    List<Post> findAllPostWithModel(String name);
}
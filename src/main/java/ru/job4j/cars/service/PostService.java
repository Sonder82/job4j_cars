package ru.job4j.cars.service;

import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> create(PostCreateDto postDto, PhotoDto photoDto);

    boolean update(Post post);

    boolean delete(int postId);

    List<PostDto> findAllOrderById();

    Optional<PostDto> findById(int postId);

    List<PostDto> findAllPostAtLastDay();

    List<PostDto> findAllPostWithPhoto();

    List<PostDto> findAllPostWithModel(String name);
}

package ru.job4j.cars.repository;

import ru.job4j.cars.model.Photo;

import java.util.Optional;

public interface PhotoRepository {

    Optional<Photo> create(Photo photo);

    boolean delete(int photoId);

    Optional<Photo> findById(int photoId);
}

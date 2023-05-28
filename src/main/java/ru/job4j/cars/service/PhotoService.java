package ru.job4j.cars.service;

import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;

import java.util.Optional;

public interface PhotoService {

    Optional<Photo> create(PhotoDto photoDto);

    boolean delete(int id);

    Optional<PhotoDto> findById(int photoId);
}



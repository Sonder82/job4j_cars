package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.PhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimplePhotoService implements PhotoService {

    private final PhotoRepository photoRepository;

    private final String storageDirectory;

    public SimplePhotoService(PhotoRepository hqlPhotoRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.photoRepository = hqlPhotoRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Photo> create(PhotoDto photoDto) {
        var path = getNewPhotoPath(photoDto.getName());
        writeFileBytes(path, photoDto.getContent());
        return photoRepository.create(new Photo(photoDto.getName(), path));
    }

    /**
     * Так создается уникальный путь для нового файла.
     * UUID это просто рандомная строка определенного формата.
     * @param sourceName имя файла Dto
     * @return строка с указанием пути для нового файла.
     */
    private String getNewPhotoPath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    /**
     * Метод выполняет запись массива байтов(контент) по указанному пути.
     * @param path путь
     * @param content контент
     */
    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int photoId) {
        var fileOptional = photoRepository.findById(photoId);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteFile(fileOptional.get().getPath());
        return photoRepository.delete(photoId);
    }

    /**
     * Метод выполняет удаление файла
     * @param path путь файла
     */
    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PhotoDto> findById(int photoId) {
        Optional<Photo> photoOptional = photoRepository.findById(photoId);
        if (photoOptional.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(photoOptional.get().getPath());
        return Optional.of(new PhotoDto(photoOptional.get().getName(), content));
    }

    /**
     * Метод выполняет побайтовое чтение из файла.
     * @param path путь файла
     * @return массив байтов
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

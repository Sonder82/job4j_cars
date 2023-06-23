package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    private final OwnerRepository ownerRepository;

    private final ModelCarRepository modelCarRepository;

    private final BodyRepository bodyRepository;

    private final EngineRepository engineRepository;

    private final TransmissionRepository transmissionRepository;

    private final CarRepository carRepository;

    private final PhotoService photoService;

    @Override
    public Optional<Post> create(PostCreateDto postDto, PhotoDto image) {
        Owner owner = new Owner();
        owner.setName(postDto.getUser().getLogin());
        owner.setUser(postDto.getUser());
        ownerRepository.create(owner);
        Car car = new Car();
        car.setName(postDto.getCarName());
        car.setModelCar(modelCarRepository.findById(postDto.getModelCarId()).get());
        car.setBody(bodyRepository.findById(postDto.getBodyId()).get());
        car.setEngine(engineRepository.findById(postDto.getEngineId()).get());
        car.setTransmission(transmissionRepository.findById(postDto.getTransmissionId()).get());
        car.setOwners(Set.of(owner));
        carRepository.create(car);
        Post post = new Post();
        post.setPrice(postDto.getPrice());
        post.setSold(false);
        post.setDescription(postDto.getDescription());
        post.setUser(postDto.getUser());
        post.setCar(car);
        saveNewFile(post, image);
        return Optional.of(postRepository.create(post).get());
    }

    private void saveNewFile(Post post, PhotoDto image) {
        var photo = photoService.create(image);
        post.setPhoto(photo.get());
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean delete(int postId) {
        var post = postRepository.findById(postId).get();
        var rsl = postRepository.delete(postId);
        photoService.delete(post.getPhoto().getId());
        return rsl;
    }

    @Override
    public List<PostDto> findAllOrderById() {
        return postRepository.findAllOrderById().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PostDto convertToDto(Post post) {
        return new PostDto(post.getId(), post.getCar().getName(), post.getCar().getModelCar().getName(),
                post.getCar().getBody().getName(), post.getCar().getEngine().getName(),
                post.getCar().getTransmission().getName(), post.getPrice(), post.getPhoto().getId(),
                post.getDescription(), post.getCreated(), post.isSold(), post.getUser());
    }

    @Override
    public boolean setSold(Integer id) {
        return postRepository.setSold(id);
    }

    @Override
    public Optional<PostDto> findById(int postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.map(this::convertToDto);
    }

    @Override
    public List<PostDto> findAllPostAtLastDay() {
        return postRepository.findAllPostAtLastDay().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findAllPostWithPhoto() {
        return postRepository.findAllPostWithPhoto().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findAllPostWithModel(String name) {
        return postRepository.findAllPostWithModel(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}

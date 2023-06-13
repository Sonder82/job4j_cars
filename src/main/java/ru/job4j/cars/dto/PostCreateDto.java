package ru.job4j.cars.dto;

import lombok.*;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostCreateDto {

    @EqualsAndHashCode.Include
    private int id;

    private String carName;

    private int modelCarId;

    private int bodyId;

    private int engineId;

    private int transmissionId;

    private int price;

    private int photoId;

    private String description;

    private LocalDateTime created;

    private boolean sold;

    private User user;
}

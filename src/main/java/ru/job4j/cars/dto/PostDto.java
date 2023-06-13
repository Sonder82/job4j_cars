package ru.job4j.cars.dto;

import lombok.*;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostDto {

    @EqualsAndHashCode.Include
    private int id;

    private String carName;

    private String modelCarName;

    private String bodyName;

    private String engineName;

    private String transmissionName;

    private int price;

    private int photoId;

    private String description;

    private LocalDateTime created;

    private boolean sold;

    private User user;
}

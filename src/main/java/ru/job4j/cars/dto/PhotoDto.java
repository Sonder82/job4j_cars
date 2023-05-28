package ru.job4j.cars.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PhotoDto {

    @EqualsAndHashCode.Include
    private final String name;

    private byte[] content;


}

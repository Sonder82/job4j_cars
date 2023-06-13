package ru.job4j.cars.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhotoDto {

    @EqualsAndHashCode.Include
    private String name;

    private byte[] content;


}

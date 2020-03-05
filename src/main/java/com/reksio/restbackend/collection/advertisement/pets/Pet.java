package com.reksio.restbackend.collection.advertisement.pets;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private String name;
    private Gender gender;
    private Integer ageInDays;
    private Type type;
}

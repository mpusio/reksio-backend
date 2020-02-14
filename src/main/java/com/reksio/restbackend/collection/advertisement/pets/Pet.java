package com.reksio.restbackend.collection.advertisement.pets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private String name;
    private Gender gender;
    private Integer ageInDays;
    private Type type;
}

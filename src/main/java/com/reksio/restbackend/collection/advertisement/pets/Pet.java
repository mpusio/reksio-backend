package com.reksio.restbackend.collection.advertisement.pets;

import com.reksio.restbackend.collection.advertisement.pets.type.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Size(max=30, message = "Pet name cannot be longer, than 30 characters.")
    private String name;
    @NotNull
    private Gender gender;
    private int ageInDays;
    private String color;
    @NotNull
    private int price;
    @NotNull
    private Type type;
}

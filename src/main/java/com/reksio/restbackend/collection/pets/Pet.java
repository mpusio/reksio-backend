package com.reksio.restbackend.collection.pets;

import com.reksio.restbackend.collection.pets.type.Type;
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
    private String gender;
    private int age;
    private String color;
    private int price;
    private Type type;
}

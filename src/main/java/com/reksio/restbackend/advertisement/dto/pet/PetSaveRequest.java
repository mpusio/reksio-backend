package com.reksio.restbackend.advertisement.dto.pet;

import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetSaveRequest {
    @Size(max=30, message = "Pet name cannot be longer, than 30 characters.")
    @NotNull
    private String name;
    @NotNull
    private Gender gender;
    @Min(0)
    @Max(40000)
    @NotNull
    private Integer ageInDays;
    @NotNull
    private Type type;
}

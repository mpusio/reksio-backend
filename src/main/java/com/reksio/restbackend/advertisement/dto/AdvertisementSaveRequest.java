package com.reksio.restbackend.advertisement.dto;

import com.reksio.restbackend.advertisement.dto.adress.AddressSaveRequest;
import com.reksio.restbackend.advertisement.dto.pet.PetSaveRequest;
import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.Contact;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
public class AdvertisementSaveRequest {
    @Size(min = 3, max=100)
    @NotNull
    private String title;
    @NotNull
    private Category category;
    @NotNull
    @Valid
    private PetSaveRequest pet;
    @Min(0)
    @Max(10000000)
    @NotNull
    private Integer price;
    @Size(min = 1, message = "Required at least one photo.")
    @NotNull
    private List<String> images;
    @Size(max = 2000, message = "Description cannot be longer than 2000 signs.")
    @NotNull
    private String description;
    @NotNull
    @Valid
    private AddressSaveRequest address;
    private String youtubeUrl;
    private Contact contact;

    public Pet getPet(){
        return Pet.builder()
                .name(this.pet.getName())
                .type(this.pet.getType())
                .gender(this.pet.getGender())
                .ageInDays(this.pet.getAgeInDays())
                .build();
    }

    public Address getAddress(){
        return Address.builder()
                .city(this.address.getCity())
                .postCode(this.address.getPostCode())
                .build();
    }
}

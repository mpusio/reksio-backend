package com.reksio.restbackend.advertisement.dto;

import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Contact;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AdvertisementUpdateRequest {
    @NotNull
    private UUID uuid;
    @Size(min = 3, max=100)
    private String title;
    @Min(0)
    @Max(10000000)
    private Integer price;
    @Size(min = 1, message = "Required at least one photo.")
    private List<byte[]> images;
    private String youtubeUrl;
    @Size(max = 2000, message = "Description cannot be longer than 2000 signs.")
    private String description;
    private Address address;
    private Contact contact;
}

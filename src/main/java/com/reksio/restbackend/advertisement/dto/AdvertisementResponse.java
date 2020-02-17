package com.reksio.restbackend.advertisement.dto;

import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.Contact;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AdvertisementResponse {
    private UUID uuid;
    private String title;
    private Category category;
    private Pet pet;
    private int price;
    private List<byte[]> images;
    private String youtubeUrl;
    private int priority;
    private String description;
    private LocalDateTime expirationDate;
    private Address address;
    private Contact contact;
    private String createdBy;

    public static AdvertisementResponse convertToAdvertisementResponse (Advertisement advertisement){
        return AdvertisementResponse.builder()
                .uuid(advertisement.getUuid())
                .title(advertisement.getTitle())
                .category(advertisement.getCategory())
                .pet(advertisement.getPet())
                .price(advertisement.getPrice())
                .images(advertisement.getImages())
                .youtubeUrl(advertisement.getYoutubeUrl())
                .description(advertisement.getDescription())
                .expirationDate(advertisement.getExpirationDate())
                .priority(advertisement.getPriority())
                .address(advertisement.getAddress())
                .contact(advertisement.getContact())
                .createdBy(advertisement.getCreatedBy())
                .build();
    }
}

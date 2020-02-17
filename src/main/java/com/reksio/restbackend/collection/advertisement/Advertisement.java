package com.reksio.restbackend.collection.advertisement;

import com.reksio.restbackend.collection.advertisement.pets.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    @Id
    private String id;
    private UUID uuid;
    private String title;
    private Category category;
    private Pet pet;
    private int price;
    private List<byte[]> images;
    private String youtubeUrl;
    private String description;
    private int priority;
    private LocalDateTime expirationDate;
    private Address address;
    private Contact contact;
    private String createdBy;
    private Date editedAt;
}

package com.reksio.restbackend.collection.advertisement;

import com.reksio.restbackend.collection.pets.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    @Id
    private String id;
    private String title;
    private List<Category> category;
    private Pet pet;
    private List<byte[]> images;
    private String youtubeUrl;
    private String description;
    private int priority = 0;
    private Date expirationDate = new Date();
    private Adress address;
    private Contact contact;
    private String createdBy;
    private Date editedAt;
}

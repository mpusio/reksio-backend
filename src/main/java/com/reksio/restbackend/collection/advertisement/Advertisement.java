package com.reksio.restbackend.collection.advertisement;

import com.reksio.restbackend.collection.pets.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max=100)
    private String title;
    @NotNull
    private Category category;
    @NotNull
    private Pet pet;
    @Size(min = 1, message = "Required at least one photo.")
    private List<byte[]> images;
    private String youtubeUrl;
    @Size(max = 2000, message = "Description cannot be longer than 2000 signs.")
    private String description;
    private int priority; //0 default
    @NotNull
    private Date expirationDate; //new Date default
    @NotNull
    private Adress address;
    @NotNull
    private Contact contact;
    @NotNull
    private String createdBy;
    private Date editedAt;
}

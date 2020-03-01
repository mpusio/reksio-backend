package com.reksio.restbackend.collection.blog;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private String id;
    private UUID uuid;
    private String title;
    private String content;
    private List<String> tags;
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime editedAt;
}
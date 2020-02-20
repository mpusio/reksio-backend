package com.reksio.restbackend.collection.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private String id;
    private String postUuid;
    private String title;
    private String content;
    private List<String> tags;
    private List<Comment> comments;
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime editedAt;
}
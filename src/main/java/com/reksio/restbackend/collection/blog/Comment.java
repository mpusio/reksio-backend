package com.reksio.restbackend.collection.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String author;
    private String email;
    private LocalDateTime date;
    private String content;
}

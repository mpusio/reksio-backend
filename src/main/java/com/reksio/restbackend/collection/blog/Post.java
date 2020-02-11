package com.reksio.restbackend.collection.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Document
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private String id;
    @Size(min = 3, max=100, message = "Title must have contain in range {3-100}")
    private String title;
    @Size(max = 30000, message = "Content cannot have more, than 30000 characters.")
    private String content; //i have no idea :)
    private List<String> tags;
    @NotEmpty
    private String createdBy; //required user email (owner)

    private Date editedAt;
}
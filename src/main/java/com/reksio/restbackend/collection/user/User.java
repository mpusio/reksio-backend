package com.reksio.restbackend.collection.user;

import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.blog.Post;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private List<Role> roles;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String description;
    private byte[] image;
    @DBRef
    private List<Advertisement> advertisements;
    @DBRef
    private List<Post> posts;
    private List<Token> tokens;
    @LastModifiedDate
    private Date editedAt;
}
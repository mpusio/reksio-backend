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
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", message = "Invalid email pattern")
    private String email;
    @Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[A-Z]).{6,16})", message = "Password must contain 6-16 characters, one capital letter, one lower letter and one number.")
    private String password;
    @Size(min = 1, message = "User require role")
    private List<Role> roles;
    //TODO email email verification
    private boolean isActive; //default should be false
    @Pattern(regexp = "[A-Z][a-z]{1,30}", message = "Required capital letter, range = {1-30}")
    private String firstName;
    @Pattern(regexp = "[A-Z][a-z]{1,30}", message = "Required capital letter, range = {1-30}")
    private String lastName;
    @Size(max = 500, message = "Description cannot be longer, than 500 characters.")
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
package com.reksio.restbackend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private List<Role> roles;
    private boolean isActive;

}
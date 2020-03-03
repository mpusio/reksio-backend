package com.reksio.restbackend.collection.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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
    private String image;
    private List<Token> tokens;
    private ActivationToken activationToken;
    private ResetPasswordToken resetPasswordToken;
    @LastModifiedDate
    private LocalDateTime editedAt;
}
package com.reksio.restbackend.user.dto;

import com.reksio.restbackend.collection.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String description;
    private byte[] image;

    public static UserProfileResponse convertFromUser(User user) {
        return UserProfileResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .description(user.getDescription())
                .image(user.getImage())
                .build();
    }
}

package com.reksio.restbackend.user.dto;

import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.blog.Post;
import com.reksio.restbackend.collection.user.Token;
import com.reksio.restbackend.collection.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserProfileResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String description;
    private byte[] image;
    private List<Advertisement> advertisements;
    private List<Post> posts;
    private List<Token> tokens;

    public static UserProfileResponse convertFromUser(User user) {
        return UserProfileResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .description(user.getDescription())
                .image(user.getImage())
                .advertisements(user.getAdvertisements())
                .posts(user.getPosts())
                .tokens(user.getTokens())
                .build();
    }
}

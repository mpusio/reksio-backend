package com.reksio.restbackend.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
public class UserUpdateProfileRequest {
    @Pattern(regexp = "[A-Z][a-z]{1,30}", message = "Required capital letter, range = {1-30}")
    private String firstName;
    @Pattern(regexp = "[A-Z][a-z]{1,30}", message = "Required capital letter, range = {1-30}")
    private String lastName;
    @Size(max = 500, message = "Description cannot be longer, than 500 characters.")
    private String description;
    private byte[] image;
}

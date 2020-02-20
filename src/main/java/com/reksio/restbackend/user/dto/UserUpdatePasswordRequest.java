package com.reksio.restbackend.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@Builder
public class UserUpdatePasswordRequest {
    private String oldPassword;
    @Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[A-Z]).{6,16})", message = "Password must contain 6-16 characters, one capital letter, one lower letter and one number.")
    private String newPassword;
}

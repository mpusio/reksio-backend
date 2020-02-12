package com.reksio.restbackend.registration;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", message = "Invalid pattern")
    private String email;

    @Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[A-Z]).{6,16})", message = "Password must contain 6-16 characters, one capital letter, one lower letter and one number.")
    private String password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

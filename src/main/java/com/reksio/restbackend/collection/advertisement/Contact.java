package com.reksio.restbackend.collection.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private String phone;
    @NotNull
    private String email;
    private String facebookUrl;
    private String details;
}

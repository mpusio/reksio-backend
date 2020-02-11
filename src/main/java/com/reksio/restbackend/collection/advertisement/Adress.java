package com.reksio.restbackend.collection.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adress {
    private String city;
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "Required format: XX-XXX")
    private String postCode;
}

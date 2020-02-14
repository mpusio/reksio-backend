package com.reksio.restbackend.advertisement.dto.adress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressSaveRequest {
    @NotEmpty
    @NotNull
    private String city;
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "Required format: XX-XXX")
    @NotNull
    private String postCode;
}
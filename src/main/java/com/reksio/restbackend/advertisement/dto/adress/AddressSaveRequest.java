package com.reksio.restbackend.advertisement.dto.adress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressSaveRequest {
    @NotNull
    private String city;
    @NotNull
    private String postCode;
}
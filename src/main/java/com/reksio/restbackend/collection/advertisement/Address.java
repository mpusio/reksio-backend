package com.reksio.restbackend.collection.advertisement;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String city;
    private String postCode;
    private double lat;
    private double lng;
}

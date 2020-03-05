package com.reksio.restbackend.collection.advertisement;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private String phone;
    private String facebookUrl;
    private String details;
}

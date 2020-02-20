package com.reksio.restbackend.token;

import com.reksio.restbackend.collection.user.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenPromoteRequest {
    private Token promoToken;
    private UUID eventUuid;
}

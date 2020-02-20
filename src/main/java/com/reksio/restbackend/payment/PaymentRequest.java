package com.reksio.restbackend.payment;

import com.reksio.restbackend.collection.user.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @NotNull
    private String paymentToken;
    @NotNull
    private Token promoToken;
    @NotNull
    @Min(1)
    private Integer amount;
}

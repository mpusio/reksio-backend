package com.reksio.restbackend.integrateTests.payment;

import com.google.gson.Gson;
import com.reksio.restbackend.collection.user.Token;
import com.reksio.restbackend.integrateTests.LoginTest;
import com.reksio.restbackend.payment.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;

    @Test
    public void shouldNotChargeWithUsedPaymentToken() throws Exception {

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(3)
                .paymentToken("tok_1GE0HWKXbRO0Khe7ZOtndDZ7")
                .promoToken(Token.BLUE)
                .build();

        userToken = shouldLoginAsUserAndReturnTokenInHeader();

        //given
        String json = new Gson().toJson(paymentRequest);

        //when, then
        this.mockMvc
                .perform(post("/api/v1/charge")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

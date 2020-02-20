package com.reksio.restbackend.integrateTests.payment;

import com.google.gson.Gson;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.user.Token;
import com.reksio.restbackend.integrateTests.prepare.LoginTest;
import com.reksio.restbackend.payment.PaymentRequest;
import com.reksio.restbackend.token.TokenPromoteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("An error occurred while trying to create a charge.")));
    }
}

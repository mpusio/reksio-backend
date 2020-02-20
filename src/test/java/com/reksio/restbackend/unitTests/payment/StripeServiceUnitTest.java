package com.reksio.restbackend.unitTests.payment;

import com.reksio.restbackend.exception.payment.ChargeFailedException;
import com.reksio.restbackend.payment.StripeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StripeServiceUnitTest {

    StripeService stripeService;

    @BeforeEach
    public void init(){
        stripeService = new StripeService();
    }

    @Test
    public void shouldNotWorkWithUsedToken(){
        //given
        String email = "example@gmail.com";
        String paymentToken = "tok_1GE0HWKXbRO0Khe7ZOtndDZ7";
        int price = 10;
        int amount = 3;
        ReflectionTestUtils.setField(stripeService,"API_SECRET_KEY","sk_test_RAMJbRSQ0OcKa8Qrrg837vHp00RA2X3zf0");

        //when, then
        assertThrows(ChargeFailedException.class, () -> stripeService.createCharge(email, paymentToken, price, amount));
    }

    @Test
    public void shouldNotWorkWithInvalidToken(){
        //given
        String email = "example@gmail.com";
        String paymentToken = "tok_dupadupa";
        int price = 10;
        int amount = 3;
        ReflectionTestUtils.setField(stripeService,"API_SECRET_KEY","sk_test_RAMJbRSQ0OcKa8Qrrg837vHp00RA2X3zf0");

        //when, then
        assertThrows(ChargeFailedException.class, () -> stripeService.createCharge(email, paymentToken, price, amount));
    }
}

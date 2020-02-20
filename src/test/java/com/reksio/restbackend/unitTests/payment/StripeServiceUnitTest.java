package com.reksio.restbackend.unitTests.payment;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.reksio.restbackend.payment.StripeService;
import com.reksio.restbackend.unitTests.LoggerTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class StripeServiceUnitTest {

    ListAppender<ILoggingEvent> loggerList;

    StripeService stripeService;

    @BeforeEach
    public void init(){
        loggerList = LoggerTestUtil.getListAppenderForClass(StripeService.class);
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

        //when
       stripeService.createCharge(email, paymentToken, price, amount);

        //then
        assertThat(loggerList.list.get(0).getMessage()).contains("You cannot use a Stripe token more than once");
        assertThat(loggerList.list.get(0).getLevel()).isEqualTo(Level.ERROR);
    }

    @Test
    public void shouldNotWorkWithInvalidToken(){
        //given
        String email = "example@gmail.com";
        String paymentToken = "tok_dupadupa";
        int price = 10;
        int amount = 3;
        ReflectionTestUtils.setField(stripeService,"API_SECRET_KEY","sk_test_RAMJbRSQ0OcKa8Qrrg837vHp00RA2X3zf0");

        //when
        stripeService.createCharge(email, paymentToken, price, amount);

        //then
        assertThat(loggerList.list.get(0).getMessage()).contains("No such token");
        assertThat(loggerList.list.get(0).getLevel()).isEqualTo(Level.ERROR);
    }
}

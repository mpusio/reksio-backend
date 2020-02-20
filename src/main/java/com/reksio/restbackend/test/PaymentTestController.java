package com.reksio.restbackend.test;

import com.reksio.restbackend.payment.StripeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentTestController {

    private StripeService stripeService;

    public PaymentTestController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/test/charge-template")
    public String paymentStripeTemplate(){
        return "charge";
    }
}

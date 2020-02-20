package com.reksio.restbackend.payment;

import com.reksio.restbackend.collection.user.Token;
import com.reksio.restbackend.exception.payment.ChargeFailedException;
import com.reksio.restbackend.security.JwtUtil;
import com.reksio.restbackend.token.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    @Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;
    private final StripeService stripeService;
    private final TokenService tokenService;

    public PaymentController(StripeService stripeService, TokenService tokenService) {
        this.stripeService = stripeService;
        this.tokenService = tokenService;
    }

    @PostMapping("/charge")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PaymentResponse createCharge(@RequestBody PaymentRequest paymentRequest, HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        String paymentToken = paymentRequest.getPaymentToken();
        Token promoToken = paymentRequest.getPromoToken();
        int amount = paymentRequest.getAmount();

        if (paymentToken == null) {
            throw new ChargeFailedException("Stripe payment token is missing. Please, try again later.");
        }

        stripeService.createCharge(email, paymentToken, promoToken.getPrice(), amount);
        tokenService.saveTokensForUser(promoToken, amount, email);

        return new PaymentResponse(true, "Your charge was success!");
    }

    @GetMapping("/charge/public-key")
    public String getPublicStripeKey(){
        return API_PUBLIC_KEY;
    }
}

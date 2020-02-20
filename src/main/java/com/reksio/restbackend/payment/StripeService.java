package com.reksio.restbackend.payment;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.reksio.restbackend.payment.Currency.PLN;

@Service
public class StripeService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    private final Logger log = LoggerFactory.getLogger(StripeService.class);

    public Optional<String> createCharge(String email, String paymentToken, int price, int amount) {
        String chargeId = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> chargeParams = chargeParams(email, paymentToken, price, amount);
            Charge charge = Charge.create(chargeParams);
            chargeId = charge.getId();
        } catch (Exception ex) {
            log.error("Charge failed. " + ex.getMessage());
        }
        return Optional.ofNullable(chargeId);
    }

    private Map<String, Object> chargeParams(String email, String paymentToken, int price, int amount){
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", summedPrice(amount, price));
        chargeParams.put("currency", PLN.name());
        chargeParams.put("description", "Charge for " + email);
        chargeParams.put("source", paymentToken);

        return chargeParams;
    }

    private int summedPrice (int amount, int price){
      return (price * 100) * amount;
    }
}

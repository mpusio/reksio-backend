package com.reksio.restbackend.token;

import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import com.reksio.restbackend.collection.user.Token;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.exception.advertisement.AdvertisementNotExistException;
import com.reksio.restbackend.exception.token.TokenNotExistException;
import com.reksio.restbackend.exception.user.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public TokenService(UserRepository userRepository, AdvertisementRepository advertisementRepository) {
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }

    public AdvertisementResponse promoteAdvertisement(String userEmail, Token promoToken, UUID eventUuid) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserExistException::new);

        Advertisement advertisement = advertisementRepository.findByUuidAndCreatedBy(eventUuid, userEmail)
                .orElseThrow(() -> new AdvertisementNotExistException("Cannot find event"));

        checkUserHavePromoTokens(user, promoToken);
        upgradeAdvertisement(advertisement, promoToken);

        return AdvertisementResponse.convertToAdvertisementResponse(advertisementRepository.save(advertisement));
    }

    private void checkUserHavePromoTokens(User user, Token promoToken){
        List<Token> tokens = user.getTokens();

        if (tokens==null || tokens.isEmpty() || !tokens.contains(promoToken)){
            throw new TokenNotExistException("User have not " + promoToken + " promo token.");
        }
    }

    private void upgradeAdvertisement(Advertisement advertisement, Token promoToken){
        advertisement.setExpirationDate(advertisement.getExpirationDate().plusDays(promoToken.getExtraTimeInDays()));
        advertisement.setPriority(promoToken.getPriority());
    }

    public void giveTokensForUser(Token token, int amount, String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserExistException::new);
        List<Token> tokens = addTokens(token, amount, user.getTokens());
        user.setTokens(tokens);
        userRepository.save(user);
    }

    private List<Token> addTokens(Token tokenType, int amount, List<Token> tokens){
        if(tokens == null){
            tokens = new ArrayList<>();
        }
        for (int i = 0; i < amount; i++) {
            tokens.add(tokenType);
        }
        return tokens;
    }
}

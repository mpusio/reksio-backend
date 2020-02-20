package com.reksio.restbackend.token;

import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(UserRepository userRepository, AdvertisementRepository advertisementRepository, TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/advertisement/promote")
    public AdvertisementResponse promoteAdvertisement(@RequestBody TokenPromoteRequest promoteRequest, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        return tokenService.promoteAdvertisement(email, promoteRequest.getPromoToken(), promoteRequest.getEventUuid());
    }
}

package com.reksio.restbackend.unitTests.token;

import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import com.reksio.restbackend.collection.user.Token;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.exception.token.TokenNotExistException;
import com.reksio.restbackend.token.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceUnitTest {

    @InjectMocks
    TokenService tokenService;

    @Mock
    UserRepository userRepository;

    @Mock
    AdvertisementRepository advertisementRepository;

    @Test
    public void shouldGiveTokensForUserWhenHeHaveNotAnyToken(){
        //given
        User user = User.builder()
                .email("email@example.com")
                .password("password")
                .build();

        //when
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        tokenService.giveTokensForUser(Token.BLUE, 3, user.getEmail());

        //then
        assertThat(user.getTokens()).isNotEmpty();
        assertThat(user.getTokens()).hasSize(3);
        assertThat(user.getTokens()).contains(Token.BLUE);
    }

    @Test
    public void shouldGiveAdditionalToken(){
        //given
        User userWithTokens = User.builder()
                .email("withToken@o2.pl")
                .password("guwno")
                .tokens(new ArrayList<>(List.of(Token.BLUE, Token.BLUE, Token.RED)))
                .build();

        //when
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userWithTokens));
        when(userRepository.save(any(User.class))).thenReturn(userWithTokens);

        tokenService.giveTokensForUser(Token.GREEN, 2, userWithTokens.getEmail());

        //then
        assertThat(userWithTokens.getTokens()).isNotEmpty();
        assertThat(userWithTokens.getTokens()).hasSize(5);
        assertThat(userWithTokens.getTokens()).containsSubsequence(Token.BLUE, Token.BLUE);
        assertThat(userWithTokens.getTokens()).containsSubsequence(Token.GREEN, Token.GREEN);
        assertThat(userWithTokens.getTokens()).contains(Token.RED);
    }

    @Test
    public void shouldPromoteAdvertisement(){
        //given
        User user = User.builder()
                .email("withToken@o2.pl")
                .password("guwno")
                .tokens(new ArrayList<>(List.of(Token.BLUE, Token.BLUE, Token.RED)))
                .build();

        UUID uuid = UUID.randomUUID();

        Advertisement advertisement = Advertisement.builder()
                .uuid(uuid)
                .title("Your advertisement")
                .expirationDate(LocalDateTime.now())
                .priority(0)
                .build();

        //when
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(advertisementRepository.findByUuidAndCreatedBy(any(UUID.class), anyString())).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(advertisement);

        tokenService.promoteAdvertisement(user.getEmail(), Token.BLUE, uuid);

        //then
        assertThat(advertisement.getPriority()).isEqualTo(Token.BLUE.getPriority());
        assertThat(advertisement.getExpirationDate()).isEqualToIgnoringMinutes(LocalDateTime.now().plusDays(Token.BLUE.getExtraTimeInDays()));
    }

    @Test
    public void shouldNotPromoteAdvertisementWithUserWithoutToken(){
        //given
        User user = User.builder()
                .email("withToken@o2.pl")
                .password("guwno")
                .build();

        UUID uuid = UUID.randomUUID();

        Advertisement advertisement = Advertisement.builder()
                .uuid(uuid)
                .title("Your advertisement")
                .expirationDate(LocalDateTime.now())
                .priority(0)
                .build();

        //when
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(advertisementRepository.findByUuidAndCreatedBy(any(UUID.class), anyString())).thenReturn(Optional.of(advertisement));

        //then
        assertThrows(TokenNotExistException.class, () -> tokenService.promoteAdvertisement(user.getEmail(), Token.BLUE, uuid));
    }
}

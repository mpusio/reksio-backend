package com.reksio.restbackend.unitTests.user;

import com.reksio.restbackend.collection.user.ActivationToken;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import com.reksio.restbackend.exception.user.TokenExpiredException;
import com.reksio.restbackend.exception.user.UserNotExistException;
import com.reksio.restbackend.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceActivationUnitTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder encoder;

    @Test
    public void shouldActivateAccount(){
        //given
        UUID tokenUuid = UUID.randomUUID();

        User user = User.builder()
                .activationToken(new ActivationToken(tokenUuid, LocalDateTime.now().plusMinutes(12)))
                .build();

        //when
        when(userRepository.findByActivationToken_Uuid(tokenUuid))
                .thenReturn(Optional.ofNullable(user));

        //then
        userService.activateAccount(tokenUuid);
    }

    @Test
    public void shouldNotActivateAccountWhenTokenIsExpired(){
        //given
        UUID tokenUuid = UUID.randomUUID();

        User user = User.builder()
                .activationToken(new ActivationToken(tokenUuid, LocalDateTime.now().minusMinutes(5)))
                .build();

        //when
        when(userRepository.findByActivationToken_Uuid(tokenUuid))
                .thenReturn(Optional.ofNullable(user));

        //then
        assertThrows(TokenExpiredException.class, () -> userService.activateAccount(tokenUuid));
    }

    @Test
    public void shouldNotActivateAccountWhenUserNotExist(){
        //when
        when(userRepository.findByActivationToken_Uuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        //then
        assertThrows(UserNotExistException.class, () -> userService.activateAccount(UUID.randomUUID()));
    }
}

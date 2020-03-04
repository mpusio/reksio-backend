package com.reksio.restbackend.unitTests.user;

import com.reksio.restbackend.collection.user.ResetPasswordToken;
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
public class UserServiceResetPasswordUnitTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder encoder;

    @Test
    public void shouldResetPassword(){
        //given
        UUID tokenUuid = UUID.randomUUID();

        User user = User.builder()
                .resetPasswordToken(new ResetPasswordToken(tokenUuid, LocalDateTime.now().plusMinutes(12)))
                .build();

        //when
        when(userRepository.findByResetPasswordToken_Uuid(tokenUuid))
                .thenReturn(Optional.ofNullable(user));

        //then
        userService.resetPassword(tokenUuid, "pass");
    }

    @Test
    public void shouldNotResetPasswordWhenTokenIsExpired(){
        //given
        UUID tokenUuid = UUID.randomUUID();

        User user = User.builder()
                .resetPasswordToken(new ResetPasswordToken(tokenUuid, LocalDateTime.now().minusMinutes(5)))
                .build();

        //when
        when(userRepository.findByResetPasswordToken_Uuid(tokenUuid))
                .thenReturn(Optional.ofNullable(user));

        //then
        assertThrows(TokenExpiredException.class, () -> userService.resetPassword(tokenUuid, "pass"));
    }

    @Test
    public void shouldNotResetPasswordWhenUserNotExist(){
        //when
        when(userRepository.findByResetPasswordToken_Uuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        //then
        assertThrows(UserNotExistException.class, () -> userService.resetPassword(UUID.randomUUID(), "pass"));
    }
}


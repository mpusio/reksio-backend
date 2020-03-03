package com.reksio.restbackend.email;

import com.reksio.restbackend.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    @Value("${activation-link}")
    private String activationLink;
    private final MailService mailService;
    private final UserService userService;

    public EmailController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping("/email/activation-link/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendActivationLinkToAccount(@PathVariable String email) throws IOException {
        UUID randomToken = UUID.randomUUID();
        userService.setActivationToken(email, randomToken);
        mailService.sendActivationLink(email, activationLink + "/" + randomToken);
    }

    @PostMapping("/email/activate-account/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateAccount(@PathVariable UUID token){
        userService.activateAccount(token);
    }

    //TODO newsletter
    public void sendNewsletterToUsers(){
        throw new RuntimeException("Not implemented yet");
    }

    //TODO reset password
    public void sendResetPasswordLink(){
        throw new RuntimeException("Not implemented yet");
    }

}

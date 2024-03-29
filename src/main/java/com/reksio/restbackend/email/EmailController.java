package com.reksio.restbackend.email;

import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    @Value("${activation-link}")
    private String activationLink;
    @Value("${reset-password-link}")
    private String resetPasswordLink;

    private final EmailService emailService;
    private final UserService userService;

    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @ApiOperation(value = "Sending email with activation link.", code = 204)
    @PostMapping("/email/send-activation-link/{emailTo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendActivationLinkToAccount(@PathVariable String emailTo) throws IOException {
        UUID randomToken = UUID.randomUUID();
        userService.setActivationToken(emailTo, randomToken);
        emailService.sendActivationEmail(emailTo, activationLink);
    }

    @ApiOperation(value = "Set account as active.", code = 204)
    @PostMapping("/email/activate-account/{activationToken}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateAccount(@PathVariable UUID activationToken){
        userService.activateAccount(activationToken);
    }

    @ApiOperation(value = "Sending email with reset password link.", code = 204)
    @PostMapping("/email/send-reset-password-link/{emailTo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendResetPasswordLink(@PathVariable String emailTo) throws IOException {
        UUID randomToken = UUID.randomUUID();
        userService.setResetPasswordToken(emailTo, randomToken);
        emailService.sendResetPasswordEmail(emailTo, resetPasswordLink);
    }

    @ApiOperation(value = "Reset password to new.", code = 204)
    @PostMapping("/email/reset-password/{resetPasswordToken}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@PathVariable UUID resetPasswordToken, @RequestParam String newPassword){
        userService.resetPassword(resetPasswordToken, newPassword);
    }

    @ApiOperation(value = "Sending newsletter to all active users. ")
    @PostMapping("/admin/email/send-newsletter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendNewsletterToActiveUsers(@RequestParam String subject, @RequestPart MultipartFile htmlFile) throws IOException {
        String content = new String(htmlFile.getBytes());

        userService.getActiveUsers().stream()
                .map(User::getEmail)
                .forEach(email -> emailService.sendMail(email, subject, content, true));
    }
}
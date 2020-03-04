package com.reksio.restbackend.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendActivationLink(String emailTo, String link) throws IOException {
        String content = loadContentTemplate("templates/activation.html");
        String preparedContent = content.replace("Here place activation link", link);
        sendMail(emailTo, "Activation link", preparedContent, true);
    }

    public void sendResetPasswordLink(String emailTo, String link) throws IOException {
        String content = loadContentTemplate("templates/reset_password.html");
        String preparedContent = content.replace("Here place activation link", link);
        sendMail(emailTo, "Reset password", preparedContent, true);
    }

    private String loadContentTemplate(String path) throws IOException {
        Path pathToTemplate = new ClassPathResource(path).getFile().toPath();
        return new String(Files.readAllBytes(pathToTemplate));
    }

    private void sendMail(String to, String subject, String text, boolean isHtmlContent){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, isHtmlContent);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e){
            log.error("Sending mail failed.", e);
        }
    }


}

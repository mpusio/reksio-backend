package com.reksio.restbackend.integrateTests.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.reksio.restbackend.email.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MailServiceTest {

    @Autowired
    MailService mailService;

    GreenMail greenMail;

    @BeforeEach
    public void initData(){
        greenMail = new GreenMail(ServerSetupTest.ALL);
        greenMail.setUser("test@gmail.com", "username", "secret");
        greenMail.start();
    }

    @Test
    public void shouldSendActivationEmail() throws Exception {
        // Given
        String to = "aloha@gmail.com";

        // When
        mailService.sendActivationLink(to, "link");

        // Then
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages.length).isEqualTo(1);

        MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getAllRecipients()[0].toString()).isEqualTo(to); //to

        InputStream inputStream = receivedMessage.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        assertThat(result).contains("<head>"); //example tag html

    }
}

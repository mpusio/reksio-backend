package com.reksio.restbackend.integrateTests;

import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.registration.RegistrationRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MockMvc mockMvc;

    private static final String EMAIL = "example@gmail.com";
    private static final String PASSWORD = "Password123";

    @Test
    @Order(1)
    public void shouldRegisterUserMvc() throws Exception {
        RegistrationRequest request= new RegistrationRequest(EMAIL, PASSWORD);
        String json = request.toString();

        this.mockMvc
                .perform(post("/api/v1/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("email")));



    }
    @Test
    @Order(2)
    public void shouldReturnConflictStatus() throws Exception {
        RegistrationRequest request= new RegistrationRequest(EMAIL, PASSWORD);
        String json = request.toString();

        this.mockMvc
                .perform(post("/api/v1/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    public void userShouldExistInDatabase(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(EMAIL));
        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users).hasSize(1);
    }

    @Test
    public void incorrectEmail() throws Exception {
        RegistrationRequest request= new RegistrationRequest("incorrect", PASSWORD);
        String json = request.toString();

        this.mockMvc
                .perform(post("/api/v1/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
}

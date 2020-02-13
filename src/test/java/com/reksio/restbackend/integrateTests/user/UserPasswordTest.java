package com.reksio.restbackend.integrateTests.user;

import com.google.gson.Gson;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.integrateTests.prepare.LoginTest;
import com.reksio.restbackend.user.dto.UserUpdatePasswordRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserPasswordTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;

    @BeforeEach
    public void initData() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    @Order(2)
    public void shouldChangePassword() throws Exception {
        UserUpdatePasswordRequest passwordRequest = UserUpdatePasswordRequest.builder()
                .newPassword("Password12")
                .oldPassword("password")
                .build();

        String json = new Gson().toJson(passwordRequest);

        this.mockMvc
                .perform(put("/api/v1/user/password")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Query query = new Query();
        query.addCriteria(Criteria.where("email").is("user@gmail.com"));
        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getPassword()).doesNotMatch("password");
    }

    @Test
    @Order(1)
    public void shouldNotChangePasswordIfOldPasswordIsIncorrect() throws Exception {
        UserUpdatePasswordRequest passwordRequest = UserUpdatePasswordRequest.builder()
                .newPassword("Password12")
                .oldPassword("dupadupa")
                .build();

        String json = new Gson().toJson(passwordRequest);

        this.mockMvc
                .perform(put("/api/v1/user/password")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

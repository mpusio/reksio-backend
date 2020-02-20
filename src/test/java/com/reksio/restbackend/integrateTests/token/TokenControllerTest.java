package com.reksio.restbackend.integrateTests.token;

import com.google.gson.Gson;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.user.Token;
import com.reksio.restbackend.integrateTests.prepare.LoginTest;
import com.reksio.restbackend.token.TokenPromoteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenControllerTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;

    private TokenPromoteRequest promoteRequest;

    @BeforeEach
    public void init() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();

        Query query = new Query();
        query.addCriteria(Criteria.where("createdBy").is("user@gmail.com"));
        Advertisement advertisement = mongoTemplate.find(query, Advertisement.class).get(0);

        promoteRequest = new TokenPromoteRequest(Token.BLUE, advertisement.getUuid());
    }

    @Test
    public void shouldPromoteAdvertisement() throws Exception {
        //given
        String json = new Gson().toJson(promoteRequest);

        //when, then
        this.mockMvc
                .perform(post("/api/v1/advertisement/promote")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

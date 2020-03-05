package com.reksio.restbackend.integrateTests.advertisement;

import com.reksio.restbackend.integrateTests.LoginTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdvertisementGetTest extends LoginTest {
    @Autowired
    MockMvc mockMvc;

    private String userToken;

    @BeforeEach
    public void initData() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    public void shouldGetAdvertisement() throws Exception {
        //when, then
        this.mockMvc
                .perform(get("/api/v1/advertisement")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

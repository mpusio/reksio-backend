package com.reksio.restbackend.integrateTests.user;

import com.reksio.restbackend.integrateTests.prepare.LoginTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    private String userToken;

    @BeforeEach
    public void initData() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    public void shouldDisplayProfile() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/user?email=user@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user@gmail.com")));
    }

//    @Test
//    public void shouldUpdateProfile(){
//
//    }

}

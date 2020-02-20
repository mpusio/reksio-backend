package com.reksio.restbackend.integrateTests.access;

import com.reksio.restbackend.integrateTests.LoginTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAccessTest extends LoginTest{

    @Autowired
    MockMvc mockMvc;

    private String adminToken;
    private String userToken;

    @BeforeAll
    public void login() throws Exception {
        adminToken = shouldLoginAsAdminAndReturnTokenInHeader();
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    public void shouldAchieveAccessWithTokenAsAdmin() throws Exception {
        this.mockMvc
                .perform(get("/test/admin")
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin access")));
    }

    @Test
    public void shouldNotAchieveAccessWithTokenAsUser() throws Exception {
        this.mockMvc
                .perform(get("/test/admin")
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAchieveAccessWithTokenAsUser() throws Exception {
        this.mockMvc
                .perform(get("/test/protected")
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test protected access")));
    }
}

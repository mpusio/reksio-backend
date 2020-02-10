package com.reksio.restbackend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public abstract class LoginTest extends InitData{

    @Autowired
    MockMvc mockMvc;


    public String shouldLoginAsAdminAndReturnTokenInHeader() throws Exception {

        String requestJson = "{\"email\":\"admin@gmail.com\", \"password\":\"password\"}";

        MvcResult mvcResult = this.mockMvc
                .perform(post("/api/v1/login").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")))
                .andExpect(header().string("Authorization", containsString("Bearer")))
                .andReturn();

        return mvcResult.getResponse().getHeader("Authorization");
    }


    public String shouldLoginAsUserAndReturnTokenInHeader() throws Exception {

        String requestJson = "{\"email\":\"user@gmail.com\", \"password\":\"password\"}";

        MvcResult mvcResult = this.mockMvc
                .perform(post("/api/v1/login").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("")))
                .andExpect(header().string("Authorization", containsString("Bearer")))
                .andReturn();

        return mvcResult.getResponse().getHeader("Authorization");
    }
}

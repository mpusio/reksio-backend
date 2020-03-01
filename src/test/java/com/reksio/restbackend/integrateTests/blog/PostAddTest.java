package com.reksio.restbackend.integrateTests.blog;

import com.google.gson.Gson;
import com.reksio.restbackend.blog.PostSaveRequest;
import com.reksio.restbackend.integrateTests.LoginTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostAddTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    private String userToken;
    private PostSaveRequest request;

    @BeforeEach
    public void initData() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    public void shouldSavePost() throws Exception {
        //given
        request = PostSaveRequest.builder()
                .title("Title Post")
                .content("Content of advertisement")
                .tags(List.of("tag1"))
                .build();

        String json = new Gson().toJson(request);

        //when, then
        this.mockMvc
                .perform(post("/api/v1/user/blog/post")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}

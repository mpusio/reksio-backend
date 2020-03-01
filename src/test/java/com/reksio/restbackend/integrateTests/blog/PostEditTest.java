package com.reksio.restbackend.integrateTests.blog;

import com.google.gson.Gson;
import com.reksio.restbackend.blog.PostEditRequest;
import com.reksio.restbackend.collection.blog.Post;
import com.reksio.restbackend.integrateTests.LoginTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostEditTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;
    private PostEditRequest request;

    @BeforeEach
    public void initData() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    public void shouldEditPost() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();

        Post post = Post.builder()
                .uuid(uuid)
                .title("Title Post")
                .content("Content of advertisement")
                .tags(List.of("tag1"))
                .createdBy("user@gmail.com")
                .build();

        mongoTemplate.insert(post, "post");

        request = PostEditRequest.builder()
                .postUuid(uuid)
                .content("Content is king!")
                .build();

        String json = new Gson().toJson(request);

        //when, then
        this.mockMvc
                .perform(put("/api/v1/user/blog/post")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

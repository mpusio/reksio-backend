package com.reksio.restbackend.integrateTests.blog;

import com.reksio.restbackend.blog.PostSaveRequest;
import com.reksio.restbackend.collection.blog.Post;
import com.reksio.restbackend.integrateTests.LoginTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostDeleteTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;
    private PostSaveRequest request;

    @BeforeEach
    public void initData() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    public void shouldDeletePost() throws Exception {
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

        //when, then
        this.mockMvc
                .perform(delete("/api/v1/user/blog/post/" + uuid)
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeletePostWithInvalidOwnerEmail() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();

        Post post = Post.builder()
                .uuid(uuid)
                .title("Title Post")
                .content("Content of advertisement")
                .tags(List.of("tag1"))
                .createdBy("exampleuser@gmail.com")
                .build();

        mongoTemplate.insert(post, "post");

        //when, then
        this.mockMvc
                .perform(delete("/api/v1/user/blog/post/" + uuid)
                        .header("Authorization", userToken))
                        //.contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

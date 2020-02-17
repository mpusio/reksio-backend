package com.reksio.restbackend.integrateTests.advertisement;

import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.integrateTests.prepare.LoginTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdvertisementDeleteTest extends LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;

    private void prepare(Advertisement request) throws Exception {
        //given
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
        mongoTemplate.insert(request, "advertisement");
    }

    @Test
    public void shouldDeleteAdvertisement() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();

        Advertisement request = Advertisement.builder()
                .uuid(uuid)
                .title("My beauty cute cat! <3")
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .createdBy("user@gmail.com")
                .build();

        prepare(request);

        //when, then
        this.mockMvc
                .perform(delete("/api/v1/advertisement?uuid=" + uuid)
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteIfUserIsNotTheOwnerTheAdvertisement() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();

        Advertisement request = Advertisement.builder()
                .title("Advertisement without uuid! <3")
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .createdBy("user@gmail.com")
                .build();

        prepare(request);

        //when, then
        this.mockMvc
                .perform(delete("/api/v1/advertisement?uuid=" + uuid)
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

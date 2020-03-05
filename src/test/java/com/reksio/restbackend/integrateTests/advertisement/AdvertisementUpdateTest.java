package com.reksio.restbackend.integrateTests.advertisement;

import com.google.gson.Gson;
import com.reksio.restbackend.advertisement.dto.AdvertisementUpdateRequest;
import com.reksio.restbackend.advertisement.dto.adress.AddressUpdateRequest;
import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.Contact;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import com.reksio.restbackend.integrateTests.LoginTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdvertisementUpdateTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;
    private UUID advUuid;

    @Test
    public void shouldUpdate() throws Exception {
        advUuid = UUID.randomUUID();
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
        mongoTemplate.insert(initAdvertisement(), "advertisement");

        //given
        AdvertisementUpdateRequest updateRequest = initAdvertisementUpdate();
        String json = new Gson().toJson(updateRequest);

        //when, then
        this.mockMvc
                .perform(put("/api/v1/user/advertisement")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"lat\":52.23244709999999,\"lng\":21.0145559")));
    }

    private Advertisement initAdvertisement(){
        return Advertisement.builder()
                .uuid(advUuid)
                .address(Address.builder()
                        .city("Gdańsk")
                        .postCode("80-288")
                        .build())
                .category(Category.CATS)
                .contact(Contact.builder()
                        .facebookUrl("https://www.facebook.com/profile.php?id=100004649430032")
                        .phone("123456789")
                        .details("Call to me at 8 pm - 9 pm")
                        .build())
                .images(List.of("path/to/image"))
                .pet(Pet.builder()
                        .gender(Gender.MALE)
                        .name("Cinamon")
                        .type(Type.American_Shorthair)
                        .ageInDays(365)
                        .build())
                .price(45)
                .title("My beauty cute cat! <3")
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .createdBy("user@gmail.com")
                .build();
    }

    private AdvertisementUpdateRequest initAdvertisementUpdate(){
        return AdvertisementUpdateRequest.builder()
                .uuid(advUuid)
                .address(AddressUpdateRequest.builder()
                        .city("Warsaw")
                        .postCode("00-020")
                        .build())
                .price(3005)
                .title("My cat! <3")
                .description("Fck IT!")
                .build();
    }
}


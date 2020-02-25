package com.reksio.restbackend.integrateTests.advertisement;

import com.google.gson.Gson;
import com.reksio.restbackend.advertisement.dto.AdvertisementSaveRequest;
import com.reksio.restbackend.advertisement.dto.adress.AddressSaveRequest;
import com.reksio.restbackend.advertisement.dto.pet.PetSaveRequest;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.Contact;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import com.reksio.restbackend.integrateTests.LoginTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdvertisementAddTest extends LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MongoTemplate mongoTemplate;

    private String userToken;
    private AdvertisementSaveRequest request;

    @BeforeEach
    public void initData() throws Exception {
        userToken = shouldLoginAsUserAndReturnTokenInHeader();
    }

    @Test
    public void shouldSaveAdvertisement() throws Exception {
        //given
        request = AdvertisementSaveRequest.builder()
                .address(AddressSaveRequest.builder()
                        .city("Warsaw")
                        .postCode("00-020")
                        .build())
                .category(Category.CATS)
                .contact(Contact.builder()
                        .facebookUrl("https://www.facebook.com/profile.php?id=100004649430032")
                        .phone("123456789")
                        .details("Call to me at 8 pm - 9 pm")
                        .build())
                .description("Look at my cat " +
                        "My cat is amazing " +
                        "Give it a lick " +
                        "Mmm , it tastes just like raisins " +
                        "Have a stroke of it's mane " +
                        "It turns into a plane " +
                        "and then it turns back again " +
                        "When you tug on it's winky ")
                .images(List.of("path/to/image"))
                .pet(PetSaveRequest.builder()
                        .gender(Gender.MALE)
                        .name("Cinamon")
                        .type(Type.American_Shorthair)
                        .ageInDays(365)
                        .build())
                .price(45)
                .title("My beauty cute cat! <3")
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .build();

        String json = new Gson().toJson(request);

        //when, then
        this.mockMvc
                .perform(post("/api/v1/user/advertisement")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotSaveWithIncorrectFields() throws Exception {
        //given
        request = AdvertisementSaveRequest.builder()
                .address(AddressSaveRequest.builder()
                        .city(" ")
                        .postCode("00-020")
                        .build())
                .category(Category.CATS)
                .contact(Contact.builder()
                        .facebookUrl("https://www.facebook.com/profile.php?id=100004649430032")
                        .phone("123456789")
                        .details("Call to me at 8 pm - 9 pm")
                        .build())
                .images(List.of("path/to/image"))
                .pet(PetSaveRequest.builder()
                        .gender(Gender.MALE)
                        .name("Cinamon")
                        .type(Type.Bulldogs)
                        .ageInDays(365)
                        .build())
                .price(-245)
                .title("My beauty cute cat! <3")
                .youtubeUrl("https://www.youtube.com/watch?v=MLtPdXR-eFw")
                .build();

        String json = new Gson().toJson(request);

        //when, then
        this.mockMvc
                .perform(post("/api/v1/user/advertisement")
                        .content(json)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string(containsString("price: must be greater than or equal to 0")))
                .andExpect(content().string(containsString("description: must not be null")));
    }
}

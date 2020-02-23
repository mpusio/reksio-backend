package com.reksio.restbackend.unitTests.filter;

import com.reksio.restbackend.advertisement.AdvertisementController;
import com.reksio.restbackend.advertisement.AdvertisementService;
import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import com.reksio.restbackend.exception.filter.FilterNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilterTest {

    //filtruj "za plecami" po prioryterze, później po expirationDate

    //filtruj po jak najbliższej lokalizacji,

    @InjectMocks
    AdvertisementController advertisementController;

    @Mock
    AdvertisementService advertisementService;

    List<Advertisement> exampleData = new ArrayList<>();

    @BeforeEach
    public void init(){
        Advertisement ad = Advertisement.builder()
                .price(20)
                .category(Category.BIRDS)
                .priority(0)
                .pet(Pet.builder()
                        .ageInDays(123)
                        .gender(Gender.MALE)
                        .type(Type.Finches)
                        .build())
                .expirationDate(LocalDateTime.now().plusDays(3))
                .build();

        Advertisement ad2 = Advertisement.builder()
                .price(120)
                .category(Category.BIRDS)
                .priority(1)
                .pet(Pet.builder()
                        .ageInDays(30)
                        .gender(Gender.MALE)
                        .type(Type.Finches)
                        .build())
                .expirationDate(LocalDateTime.now().plusDays(1))

                .build();

        Advertisement ad3 = Advertisement.builder()
                .price(110)
                .category(Category.BIRDS)
                .priority(1)
                .pet(Pet.builder()
                        .ageInDays(365)
                        .gender(Gender.FEMALE)
                        .type(Type.Finches)
                        .build())
                .expirationDate(LocalDateTime.now().plusDays(2))
                .build();

        Advertisement ad4 = Advertisement.builder()
                .price(200)
                .category(Category.DOGS)
                .priority(1)
                .pet(Pet.builder()
                        .ageInDays(1234)
                        .gender(Gender.FEMALE)
                        .type(Type.Bulldogs)
                        .build())
                .expirationDate(LocalDateTime.now().plusDays(10))
                .build();

        exampleData.addAll(List.of(ad, ad2, ad3, ad4));
    }

    @Test
    public void shouldFilterAdvertisementsWithCorrectParameters(){
        //given
        Map<String, String> params = new HashMap<>() {{
            put("category", Category.BIRDS.name());
        }};

        //when
        when(advertisementService.getAllAdvertisements()).thenReturn(exampleData);

        //then
        List<AdvertisementResponse> filteredAds = advertisementController.getFilteredAdvertisements(params);

        assertThat(filteredAds).hasSize(3);
        assertThat(filteredAds.get(0))
                .hasFieldOrPropertyWithValue("priority", 1)
                .hasFieldOrPropertyWithValue("price", 110);
        assertThat(filteredAds.get(0).getExpirationDate()).isEqualToIgnoringHours(LocalDateTime.now().plusDays(2));

    }

    @Test
    public void shouldReturnZeroWithIncorrectValue(){
        //given
        Map<String, String> params = new HashMap<>() {{
            put("category", "dupa");
        }};

        //when
        when(advertisementService.getAllAdvertisements()).thenReturn(exampleData);

        //then
        List<AdvertisementResponse> filteredAds = advertisementController.getFilteredAdvertisements(params);

        assertThat(filteredAds).hasSize(0);
    }

    @Test
    public void shouldNotFilterWithUnknowParameter(){
        //given
        Map<String, String> params = new HashMap<>() {{
            put("catteggorry", "DOGS");
        }};

        //when
        when(advertisementService.getAllAdvertisements()).thenReturn(exampleData);

        //then
        assertThrows(FilterNotFoundException.class, () -> advertisementController.getFilteredAdvertisements(params));
    }
}

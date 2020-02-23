package com.reksio.restbackend.unitTests.filter;


import com.reksio.restbackend.advertisement.AdvertisementController;
import com.reksio.restbackend.advertisement.AdvertisementService;
import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SortTest {

    //filtruj "za plecami" po prioryterze, później po expirationDate

    //filtruj po jak najbliższej lokalizacji,

    @InjectMocks
    AdvertisementController advertisementController;

    @Mock
    AdvertisementService advertisementService;

    @Test
    public void shouldSortByPriorityAndNextByExpirationDate(){
        //given
        Advertisement ad = Advertisement.builder()
                .priority(4)
                .expirationDate(LocalDateTime.now().plusDays(5))
                .build();

        Advertisement ad2 = Advertisement.builder()
                .priority(4)
                .expirationDate(LocalDateTime.now().plusDays(7))
                .build();

        Advertisement ad3 = Advertisement.builder()
                .priority(2)
                .expirationDate(LocalDateTime.now().plusDays(3))
                .build();

        Advertisement ad4 = Advertisement.builder()
                .priority(4)
                .expirationDate(LocalDateTime.now().plusDays(2))
                .build();

        List<Advertisement> exampleData = List.of(ad4, ad2, ad, ad3);

        //when
        when(advertisementService.getAllAdvertisements()).thenReturn(exampleData);

        //then
        List<AdvertisementResponse> filtered = advertisementController.getFilteredAdvertisements(Map.of());

        assertThat(filtered).hasSize(4);
        assertThat(filtered.get(0)).isEqualToComparingFieldByField(ad2);
        assertThat(filtered.get(1)).isEqualToComparingFieldByField(ad);
        assertThat(filtered.get(2)).isEqualToComparingFieldByField(ad4);
        assertThat(filtered.get(3)).isEqualToComparingFieldByField(ad3);

    }
}
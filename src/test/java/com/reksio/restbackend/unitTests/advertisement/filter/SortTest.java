package com.reksio.restbackend.unitTests.advertisement.filter;


import com.google.maps.model.LatLng;
import com.reksio.restbackend.advertisement.AdvertisementController;
import com.reksio.restbackend.advertisement.AdvertisementService;
import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SortTest {

    //filter by the

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
        List<AdvertisementResponse> filtered = advertisementController.getFilteredAdvertisements(Map.of(), null);

        assertThat(filtered).hasSize(4);
        assertThat(filtered.get(0)).isEqualToComparingFieldByField(ad2);
        assertThat(filtered.get(1)).isEqualToComparingFieldByField(ad);
        assertThat(filtered.get(2)).isEqualToComparingFieldByField(ad4);
        assertThat(filtered.get(3)).isEqualToComparingFieldByField(ad3);

    }

    @Test
    public void shouldSortByPriorityAndNextByNearestLocationAndNextByExpirationDate(){
        //given
        Advertisement ad = Advertisement.builder()
                .priority(4)
                .expirationDate(LocalDateTime.now().plusDays(5))
                .address(Address.builder()
                        .lat(50.0)
                        .lng(50.0)
                        .build())
                .build();

        Advertisement ad2 = Advertisement.builder()
                .priority(4)
                .expirationDate(LocalDateTime.now().plusDays(7))
                .address(Address.builder()
                        .lat(60.0)
                        .lng(60.0)
                        .build())
                .build();

        Advertisement ad3 = Advertisement.builder()
                .priority(2)
                .address(Address.builder()
                        .lat(40.0)
                        .lng(40.0)
                        .build())
                .expirationDate(LocalDateTime.now().plusDays(3))
                .build();

        Advertisement ad4 = Advertisement.builder()
                .priority(4)
                .address(Address.builder()
                        .lat(20.0)
                        .lng(20.0)
                        .build())
                .expirationDate(LocalDateTime.now().plusDays(2))
                .build();

        List<Advertisement> exampleData = List.of(ad4, ad2, ad, ad3);

        //when
        when(advertisementService.getAllAdvertisements()).thenReturn(exampleData);

        //then
        List<AdvertisementResponse> filtered = advertisementController.getFilteredAdvertisements(Map.of(), new LatLng(0.0, 0.0));

        assertThat(filtered).hasSize(4);
        assertThat(filtered.get(0)).isEqualToComparingFieldByField(ad4);
        assertThat(filtered.get(1)).isEqualToComparingFieldByField(ad);
        assertThat(filtered.get(2)).isEqualToComparingFieldByField(ad2);
        assertThat(filtered.get(3)).isEqualToComparingFieldByField(ad3);

    }

}
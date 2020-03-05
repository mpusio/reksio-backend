package com.reksio.restbackend.unitTests.advertisement.location;

import com.google.maps.model.LatLng;
import com.reksio.restbackend.advertisement.location.DistanceComparator;
import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceComparatorTest {

    @Test
    public void shouldSortByDistance(){
        //given
        Advertisement ad1 = Advertisement.builder()
                .address(Address.builder()
                        .lat(80.0)
                        .lng(90.0)
                        .build())
                .build();

        Advertisement ad2 = Advertisement.builder()
                .address(Address.builder()
                        .lat(100.0)
                        .lng(110.0)
                        .build())
                .build();

        Advertisement ad3 = Advertisement.builder()
                .address(Address.builder()
                        .lat(20.0)
                        .lng(10.0)
                        .build())
                .build();

        Advertisement ad4 = Advertisement.builder()
                .address(Address.builder()
                        .lat(50.0)
                        .lng(50.0)
                        .build())
                .build();

        List<Advertisement> ads = List.of(ad1, ad2, ad3, ad4);
        LatLng waypoint = new LatLng(0.0, 0.0);

        //when
        List<Advertisement> collect = ads.stream()
                .sorted(new DistanceComparator(waypoint))
                .collect(Collectors.toList());

        //then
        assertThat(collect.get(0)).isEqualToComparingFieldByField(ad3);
        assertThat(collect.get(1)).isEqualToComparingFieldByField(ad4);
        assertThat(collect.get(2)).isEqualToComparingFieldByField(ad1);
        assertThat(collect.get(3)).isEqualToComparingFieldByField(ad2);
    }

    @Test
    public void shouldKeepDefaultOrderWhenLatLngIsNull(){
        //given
        Advertisement ad1 = Advertisement.builder()
                .address(Address.builder()
                        .lat(80.0)
                        .lng(90.0)
                        .build())
                .build();

        Advertisement ad2 = Advertisement.builder()
                .address(Address.builder()
                        .lat(100.0)
                        .lng(110.0)
                        .build())
                .build();

        Advertisement ad3 = Advertisement.builder()
                .address(Address.builder()
                        .lat(20.0)
                        .lng(10.0)
                        .build())
                .build();

        List<Advertisement> ads = List.of(ad1, ad2, ad3);
        LatLng waypoint = null;

        //when
        List<Advertisement> collect = ads.stream()
                .sorted(new DistanceComparator(waypoint))
                .collect(Collectors.toList());

        //then
        assertThat(collect.get(0)).isEqualToComparingFieldByField(ad1);
        assertThat(collect.get(1)).isEqualToComparingFieldByField(ad2);
        assertThat(collect.get(2)).isEqualToComparingFieldByField(ad3);
    }
}

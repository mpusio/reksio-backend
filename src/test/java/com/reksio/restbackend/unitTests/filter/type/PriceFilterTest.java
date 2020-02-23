package com.reksio.restbackend.unitTests.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.advertisement.filter.type.PriceFilter;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceFilterTest {

    static List<Advertisement> exampleData;

    @BeforeAll
    public static void initDataToFilter(){
        Advertisement ad1 = Advertisement.builder()
                .price(100)
                .build();

        Advertisement ad2 = Advertisement.builder()
                .price(90)
                .build();

        Advertisement ad3 = Advertisement.builder()
                .price(210)
                .build();

        Advertisement ad4 = Advertisement.builder()
                .price(300)
                .build();

        Advertisement ad5 = Advertisement.builder()
                .price(0)
                .build();

        Advertisement ad6 = Advertisement.builder()
                .price(120)
                .build();

        exampleData = List.of(ad1, ad2, ad3, ad4, ad5, ad6);
    }

    @Test
    public void shouldFilterPricesBiggerThan100(){
        //given
        Filter priceFilter = new PriceFilter("<100");

        //when
        List<Advertisement> filter = priceFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(3));

        List<Integer> prices = filter.stream().map(Advertisement::getPrice).collect(Collectors.toList());
        assertThat(prices, everyItem(greaterThan(100)));
    }

    @Test
    public void shouldFilterPricesLowerThan100(){
        //given
        Filter priceFilter = new PriceFilter(">100");

        //when
        List<Advertisement> filter = priceFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(2));

        List<Integer> prices = filter.stream().map(Advertisement::getPrice).collect(Collectors.toList());
        assertThat(prices, everyItem(lessThan(100)));
    }

    @Test
    public void shouldFilterPricesEqualsTo100(){
        //given
        Filter priceFilter = new PriceFilter("100");

        //when
        List<Advertisement> filter = priceFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(1));
    }

    @Test
    public void shouldThrowNumberFormatException(){
        //given
        Filter priceFilter = new PriceFilter("<<100");

        //when, then
        assertThrows(NumberFormatException.class, () -> priceFilter.filter(exampleData));
    }

    @Test
    public void shouldFindFreeAd(){
        //given
        Filter priceFilter = new PriceFilter("0");

        //when
        List<Advertisement> filter = priceFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(1));
    }
}
package com.reksio.restbackend.unitTests.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.advertisement.filter.type.CategoryFilter;
import com.reksio.restbackend.advertisement.filter.type.CityFilter;
import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class CityFilterTest {
    static List<Advertisement> exampleData;

    @BeforeAll
    public static void initDataToFilter(){
        Advertisement ad1 = Advertisement.builder()
                .address(new Address("Warszawa", ""))
                .build();

        Advertisement ad2 = Advertisement.builder()
                .address(new Address("Warszawa", ""))
                .build();

        Advertisement ad3 = Advertisement.builder()
                .address(new Address("Gdańsk", ""))
                .build();

        Advertisement ad4 = Advertisement.builder()
                .address(new Address("Gdańsk", ""))
                .build();

        Advertisement ad5 = Advertisement.builder()
                .address(new Address("Gdańsk", ""))
                .build();

        Advertisement ad6 = Advertisement.builder()
                .address(new Address("Sopot", ""))
                .build();

        exampleData = List.of(ad1, ad2, ad3, ad4, ad5, ad6);
    }

    @Test
    public void shouldFilterByWarszawaCity(){
        //given
        Filter cityFilter = new CityFilter("warszawa");

        //when
        List<Advertisement> filter = cityFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(2));
    }

    @Test
    public void shoulReturnEmptyFilterByUnknowCity(){
        //given
        Filter cityFilter = new CityFilter("kunegunda");

        //when
        List<Advertisement> filter = cityFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(0));
    }

}

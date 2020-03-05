package com.reksio.restbackend.unitTests.advertisement.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.advertisement.filter.type.CategoryFilter;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class CategoryFilterTest {
    static List<Advertisement> exampleData;

    @BeforeAll
    public static void initDataToFilter(){
        Advertisement ad1 = Advertisement.builder()
                .category(Category.CATS)
                .build();

        Advertisement ad2 = Advertisement.builder()
                .category(Category.CATS)
                .build();

        Advertisement ad3 = Advertisement.builder()
                .category(Category.CATS)
                .build();

        Advertisement ad4 = Advertisement.builder()
                .category(Category.DOGS)
                .build();

        Advertisement ad5 = Advertisement.builder()
                .category(Category.DOGS)
                .build();

        Advertisement ad6 = Advertisement.builder()
                .category(Category.BIRDS)
                .build();

        exampleData = List.of(ad1, ad2, ad3, ad4, ad5, ad6);
    }

    @Test
    public void shouldFilterByCategoryCats(){
        //given
        Filter categoryFilter = new CategoryFilter("CATS");

        //when
        List<Advertisement> filter = categoryFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(3));
    }

    @Test
    public void shoulReturnZeroFilterByUnknowCategory(){
        //given
        Filter categoryFilter = new CategoryFilter("DUCKS");

        //when
        List<Advertisement> filter = categoryFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(0));
    }
}

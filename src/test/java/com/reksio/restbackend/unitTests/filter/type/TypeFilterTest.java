package com.reksio.restbackend.unitTests.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.advertisement.filter.type.PetTypeFilter;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class TypeFilterTest {
    static List<Advertisement> exampleData;

    @BeforeAll
    public static void initDataToFilter(){
        Advertisement ad1 = Advertisement.builder()
                .pet(Pet.builder().type(Type.Finches).build())
                .build();

        Advertisement ad2 = Advertisement.builder()
                .pet(Pet.builder().type(Type.Finches).build())
                .build();

        Advertisement ad3 = Advertisement.builder()
                .pet(Pet.builder().type(Type.Finches).build())
                .build();

        Advertisement ad4 = Advertisement.builder()
                .pet(Pet.builder().type(Type.Bulldogs).build())
                .build();

        Advertisement ad5 = Advertisement.builder()
                .pet(Pet.builder().type(Type.Bulldogs).build())
                .build();

        Advertisement ad6 = Advertisement.builder()
                .pet(Pet.builder().type(Type.Chimpanzee).build())
                .build();

        exampleData = List.of(ad1, ad2, ad3, ad4, ad5, ad6);
    }

    @Test
    public void shouldFilterTypeFinches(){
        //given
        Filter petTypeFilter = new PetTypeFilter(Type.Finches.name());

        //when
        List<Advertisement> filter = petTypeFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(3));
    }

    @Test
    public void shouldReturnZeroWithIncorrectType(){
        //given
        Filter petTypeFilter = new PetTypeFilter("szympans");

        //when
        List<Advertisement> filter = petTypeFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(0));
    }
}

package com.reksio.restbackend.unitTests.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.advertisement.filter.type.PetAgeFilter;
import com.reksio.restbackend.advertisement.filter.type.PetGenderFilter;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgeFilterTest {

    static List<Advertisement> exampleData;

    @BeforeAll
    public static void initDataToFilter(){
        Advertisement ad1 = Advertisement.builder()
                .pet(Pet.builder().ageInDays(123).build())
                .build();

        Advertisement ad2 = Advertisement.builder()
                .pet(Pet.builder().ageInDays(163).build())
                .build();

        Advertisement ad3 = Advertisement.builder()
                .pet(Pet.builder().ageInDays(113).build())
                .build();

        Advertisement ad4 = Advertisement.builder()
                .pet(Pet.builder().ageInDays(366).build())
                .build();

        Advertisement ad5 = Advertisement.builder()
                .pet(Pet.builder().ageInDays(12).build())
                .build();

        Advertisement ad6 = Advertisement.builder()
                .pet(Pet.builder().ageInDays(13).build())
                .build();

        exampleData = List.of(ad1, ad2, ad3, ad4, ad5, ad6);
    }

    @Test
    public void shouldFilterAgeLowerThan200(){
        //given
        Filter petAgeFilter = new PetAgeFilter(">200");

        //when
        List<Advertisement> filter = petAgeFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(5));

        List<Integer> ages = filter.stream().map(advertisement -> advertisement.getPet().getAgeInDays()).collect(Collectors.toList());
        assertThat(ages, everyItem(lessThan(200)));
    }


    @Test
    public void shouldThrowNumberFormatExceptionForWrongValue(){
        //given
        Filter petAgeFilter = new PetAgeFilter(">>200");

        //when, then
        assertThrows(NumberFormatException.class, () -> petAgeFilter.filter(exampleData));
    }
}

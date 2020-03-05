package com.reksio.restbackend.unitTests.advertisement.filter.type;

import com.reksio.restbackend.advertisement.filter.Filter;
import com.reksio.restbackend.advertisement.filter.type.PetGenderFilter;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.pets.Gender;
import com.reksio.restbackend.collection.advertisement.pets.Pet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class GenderFilterTest {

    static List<Advertisement> exampleData;

    @BeforeAll
    public static void initDataToFilter(){
        Advertisement ad1 = Advertisement.builder()
                .pet(Pet.builder().gender(Gender.MALE).build())
                .build();

        Advertisement ad2 = Advertisement.builder()
                .pet(Pet.builder().gender(Gender.MALE).build())
                .build();

        Advertisement ad3 = Advertisement.builder()
                .pet(Pet.builder().gender(Gender.MALE).build())
                .build();

        Advertisement ad4 = Advertisement.builder()
                .pet(Pet.builder().gender(Gender.FEMALE).build())
                .build();

        Advertisement ad5 = Advertisement.builder()
                .pet(Pet.builder().gender(Gender.FEMALE).build())
                .build();

        Advertisement ad6 = Advertisement.builder()
                .pet(Pet.builder().gender(Gender.ASEXUAL).build())
                .build();

        exampleData = List.of(ad1, ad2, ad3, ad4, ad5, ad6);
    }

    @Test
    public void shouldFilterGenderMale(){
        //given
        Filter genderFilter = new PetGenderFilter(Gender.MALE.name());

        //when
        List<Advertisement> filter = genderFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(3));
    }

    @Test
    public void shouldReturnZeroWithIncorrectGender(){
        //given
        Filter genderFilter = new PetGenderFilter("kobieta");

        //when
        List<Advertisement> filter = genderFilter.filter(exampleData);

        //then
        assertThat(filter, hasSize(0));
    }
}

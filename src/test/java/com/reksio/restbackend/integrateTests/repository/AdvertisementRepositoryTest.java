package com.reksio.restbackend.integrateTests.repository;

import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdvertisementRepositoryTest {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testQueryMethodGreaterThan(){
        //given
        Advertisement ad1 = Advertisement.builder()
                .expirationDate(LocalDateTime.now().minusDays(5))
                .build();

        Advertisement ad2 = Advertisement.builder()
                .expirationDate(LocalDateTime.now().minusDays(3))
                .build();

        Advertisement ad3 = Advertisement.builder()
                .expirationDate(LocalDateTime.now().minusDays(2))
                .build();

        Advertisement ad4 = Advertisement.builder()
                .expirationDate(LocalDateTime.now().plusDays(2))
                .build();

        Advertisement ad5 = Advertisement.builder()
                .expirationDate(LocalDateTime.now().plusDays(5))
                .build();

        mongoTemplate.insert(ad1, "advertisement");
        mongoTemplate.insert(ad2, "advertisement");
        mongoTemplate.insert(ad3, "advertisement");
        mongoTemplate.insert(ad4, "advertisement");
        mongoTemplate.insert(ad5, "advertisement");

        //when
        advertisementRepository.deleteAllByExpirationDateLessThan(LocalDateTime.now());

        List<Advertisement> all = mongoTemplate.findAll(Advertisement.class);

        //then
        assertThat(all).hasSize(5);
    }
}

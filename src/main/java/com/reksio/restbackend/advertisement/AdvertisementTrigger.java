package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdvertisementTrigger {

    private final AdvertisementRepository advertisementRepository;

    private final Logger log = LoggerFactory.getLogger(AdvertisementTrigger.class);

    @Autowired
    public AdvertisementTrigger(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Scheduled(fixedRate = 12 * 60 * 60 * 1000) //run every 12 hours
    public void deleteAdvertisementsWithExpiredData(){
        advertisementRepository.deleteAllByExpirationDateLessThan(LocalDateTime.now());
        log.info("Deleted expired advertisements. Delete time " + LocalDateTime.now());
    }
}
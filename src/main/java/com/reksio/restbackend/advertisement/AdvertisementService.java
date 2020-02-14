package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.advertisement.dto.AdvertisementSaveRequest;
import com.reksio.restbackend.advertisement.dto.AdvertisementUpdateRequest;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import com.reksio.restbackend.exception.advertisement.AdvertisementFailedDeleteExcetion;
import com.reksio.restbackend.exception.advertisement.AdvertisementNotExistException;
import com.reksio.restbackend.exception.advertisement.AdvertisementOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public AdvertisementResponse addAdvertisementForUser(String email, AdvertisementSaveRequest request) {
        Advertisement advertisement = Advertisement.builder()
                .uuid(UUID.randomUUID())
                .title(request.getTitle())
                .category(request.getCategory())
                .pet(request.getPet())
                .price(request.getPrice())
                .images(request.getImages())
                .youtubeUrl(request.getYoutubeUrl())
                .description(request.getDescription())
                .priority(0)
                .expirationDate(thirtyDaysExpirationTime())
                .address(request.getAddress())
                .contact(request.getContact())
                .createdBy(email)
                .build();

        Advertisement insert = advertisementRepository.insert(advertisement);
        return AdvertisementResponse.convertToAdvertisementResponse(insert);
    }

    private LocalDateTime thirtyDaysExpirationTime(){
        return LocalDateTime.now().plusDays(30);
    }

    public AdvertisementResponse getAdvertisement(UUID uuid) {
        Advertisement advertisement = advertisementRepository.findByUuid(uuid).orElseThrow(() -> new AdvertisementNotExistException("Cannot find advertisement with uuid " + uuid));
        return AdvertisementResponse.convertToAdvertisementResponse(advertisement);
    }

    public AdvertisementResponse updateAdvertisement(String email, AdvertisementUpdateRequest request) {
        UUID uuid = request.getUuid();
        Advertisement advertisement = advertisementRepository.findByUuid(uuid).orElseThrow(() -> new AdvertisementNotExistException("Cannot find advertisement with uuid " + uuid));
        if(advertisement.getCreatedBy().equals(email)) throw new AdvertisementOwnerException("User " + email + "is not the owner of advertisement");

        advertisement.setTitle(nullChecker(request.getTitle(), advertisement.getTitle()));
        advertisement.setPrice(nullChecker(request.getPrice(), advertisement.getPrice()));
        advertisement.setImages(nullChecker(request.getImages(), advertisement.getImages()));
        advertisement.setYoutubeUrl(nullChecker(request.getYoutubeUrl(), advertisement.getYoutubeUrl()));
        advertisement.setDescription(nullChecker(request.getDescription(), advertisement.getDescription()));
        advertisement.setAddress(nullChecker(request.getAddress(), advertisement.getAddress()));
        advertisement.setContact(nullChecker(request.getContact(), advertisement.getContact()));

        return AdvertisementResponse.convertToAdvertisementResponse(advertisementRepository.save(advertisement));
    }

    private <T> T nullChecker(T fieldToInsert, T actualField){
        if (fieldToInsert == null){
            return actualField;
        }
        return fieldToInsert;
    }

    public void deleteAdvertisement(String email, UUID uuid){
        Long deleteResult = advertisementRepository.deleteByUuidAndCreatedBy(uuid, email);
        if (deleteResult==0L){
            throw new AdvertisementFailedDeleteExcetion("Cannot delete advertisement " + uuid);
        }
    }
}

package com.reksio.restbackend.advertisement;

import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.advertisement.dto.AdvertisementSaveRequest;
import com.reksio.restbackend.advertisement.dto.AdvertisementUpdateRequest;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import com.reksio.restbackend.exception.advertisement.AdvertisementFailedDeleteExcetion;
import com.reksio.restbackend.exception.advertisement.AdvertisementInvalidFieldException;
import com.reksio.restbackend.exception.advertisement.AdvertisementNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.reksio.restbackend.advertisement.dto.AdvertisementResponse.convertToAdvertisementResponse;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public AdvertisementResponse addAdvertisementForUser(String email, AdvertisementSaveRequest request) {
        checkTypeBelongToCategory(request.getPet().getType(), request.getCategory());

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
        return convertToAdvertisementResponse(insert);
    }

    private void checkTypeBelongToCategory(Type type, Category category){
        if (!type.isInCategory(category)){
            throw new AdvertisementInvalidFieldException("Type " + type + " do not belong to " + category + " category.");
        }
    }

    private LocalDateTime thirtyDaysExpirationTime(){
        return LocalDateTime.now().plusDays(30);
    }

    public AdvertisementResponse getAdvertisement(UUID uuid) {
        Advertisement advertisement = advertisementRepository.findByUuid(uuid).orElseThrow(() -> new AdvertisementNotExistException("Cannot find advertisement with uuid " + uuid));
        return convertToAdvertisementResponse(advertisement);
    }

    public AdvertisementResponse updateAdvertisement(String email, AdvertisementUpdateRequest request) {
        UUID uuid = request.getUuid();
        Advertisement advertisement = advertisementRepository.findByUuidAndCreatedBy(uuid, email)
                .orElseThrow(() -> new AdvertisementNotExistException("Cannot find advertisement with uuid " + uuid + " and createdBy " + email));

        advertisement.setTitle(nullChecker(request.getTitle(), advertisement.getTitle()));
        advertisement.setPrice(nullChecker(request.getPrice(), advertisement.getPrice()));
        advertisement.setImages(nullChecker(request.getImages(), advertisement.getImages()));
        advertisement.setYoutubeUrl(nullChecker(request.getYoutubeUrl(), advertisement.getYoutubeUrl()));
        advertisement.setDescription(nullChecker(request.getDescription(), advertisement.getDescription()));
        advertisement.setAddress(nullChecker(request.getAddress(), advertisement.getAddress()));
        advertisement.setContact(nullChecker(request.getContact(), advertisement.getContact()));

        return convertToAdvertisementResponse(advertisementRepository.save(advertisement));
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
            throw new AdvertisementFailedDeleteExcetion("Cannot delete advertisement with uuid: " + uuid);
        }
    }

    public List<AdvertisementResponse> getAllAdvertisementsBelongToUser(String email) {
        return advertisementRepository.findAllByCreatedBy(email).stream()
                .map(AdvertisementResponse::convertToAdvertisementResponse)
                .collect(Collectors.toList());
    }

    public List<Advertisement> getAllAdvertisements() {
        return advertisementRepository.findAll();
    }
}

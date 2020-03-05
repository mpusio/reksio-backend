package com.reksio.restbackend.advertisement;

import com.google.maps.model.LatLng;
import com.reksio.restbackend.advertisement.dto.AdvertisementResponse;
import com.reksio.restbackend.advertisement.dto.AdvertisementSaveRequest;
import com.reksio.restbackend.advertisement.dto.AdvertisementUpdateRequest;
import com.reksio.restbackend.advertisement.dto.adress.AddressUpdateRequest;
import com.reksio.restbackend.advertisement.location.GoogleMapsService;
import com.reksio.restbackend.collection.advertisement.Address;
import com.reksio.restbackend.collection.advertisement.Advertisement;
import com.reksio.restbackend.collection.advertisement.AdvertisementRepository;
import com.reksio.restbackend.collection.advertisement.Category;
import com.reksio.restbackend.collection.advertisement.pets.Type;
import com.reksio.restbackend.exception.advertisement.AdvertisementFailedDeleteExcetion;
import com.reksio.restbackend.exception.advertisement.AdvertisementInvalidFieldException;
import com.reksio.restbackend.exception.advertisement.AdvertisementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.reksio.restbackend.advertisement.dto.AdvertisementResponse.convertToAdvertisementResponse;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final GoogleMapsService googleMapsService;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, GoogleMapsService googleMapsService) {
        this.advertisementRepository = advertisementRepository;
        this.googleMapsService = googleMapsService;
    }

    public AdvertisementResponse addAdvertisementForUser(String email, AdvertisementSaveRequest request) {
        checkTypeBelongToCategory(request.getPet().getType(), request.getCategory());

        Optional<LatLng> latLng = googleMapsService
                .recognizeLatLngParams(request.getAddress().getCity(), request.getAddress().getPostCode());

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
                .address(Address.builder()
                        .city(request.getAddress().getCity())
                        .postCode(request.getAddress().getPostCode())
                        .lat(latLng.get().lat)
                        .lng(latLng.get().lng)
                        .build())
                .contact(request.getContact())
                .createdBy(email)
                .build();

        return convertToAdvertisementResponse(advertisementRepository.insert(advertisement));
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
        Advertisement advertisement = advertisementRepository.findByUuid(uuid)
                .orElseThrow(() -> new AdvertisementNotFoundException("Cannot find advertisement with uuid " + uuid));
        return convertToAdvertisementResponse(advertisement);
    }

    public AdvertisementResponse updateAdvertisement(String email, AdvertisementUpdateRequest request) {
        UUID uuid = request.getUuid();
        Advertisement advertisement = advertisementRepository.findByUuidAndCreatedBy(uuid, email)
                .orElseThrow(() -> new AdvertisementNotFoundException("Cannot find advertisement with uuid " + uuid + " and createdBy " + email));

        advertisement.setTitle(nullChecker(request.getTitle(), advertisement.getTitle()));
        advertisement.setPrice(nullChecker(request.getPrice(), advertisement.getPrice()));
        advertisement.setImages(nullChecker(request.getImages(), advertisement.getImages()));
        advertisement.setYoutubeUrl(nullChecker(request.getYoutubeUrl(), advertisement.getYoutubeUrl()));
        advertisement.setDescription(nullChecker(request.getDescription(), advertisement.getDescription()));
        advertisement.setAddress(addressChecker(request.getAddress(), advertisement.getAddress()));
        advertisement.setContact(nullChecker(request.getContact(), advertisement.getContact()));

        return convertToAdvertisementResponse(advertisementRepository.save(advertisement));
    }

    private Address addressChecker(AddressUpdateRequest addressToInsert, Address actualAddress){
        Address address = actualAddress;

        if (addressToInsert != null){
            address = Address.builder()
                    .city(addressToInsert.getCity())
                    .postCode(addressToInsert.getPostCode())
                    .build();
        }

        Optional<LatLng> latLng = googleMapsService
                .recognizeLatLngParams(address.getCity(), address.getPostCode());

        if (latLng.isPresent()){
            address.setLat(latLng.get().lat);
            address.setLng(latLng.get().lng);
        }

        return address;
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

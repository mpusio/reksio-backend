package com.reksio.restbackend.collection.advertisement;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends MongoRepository<Advertisement, String> {
    Optional<Advertisement> findByUuid(UUID uuid);
    Long deleteByUuidAndCreatedBy(UUID uuid, String createdBy);
}

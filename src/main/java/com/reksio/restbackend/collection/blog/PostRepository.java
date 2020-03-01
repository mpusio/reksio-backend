package com.reksio.restbackend.collection.blog;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findByUuidAndCreatedBy(UUID uuid, String createdBy);
    Long deleteByUuidAndCreatedBy(UUID uuid, String createdBy);
    Optional<Post> findByUuid(UUID uuid);
    List<Post> findAllByCreatedBy(String createdBy);
    List<Post> findAllByOrderByIdDesc();
}

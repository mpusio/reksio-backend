package com.reksio.restbackend.collection.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationToken_Uuid(UUID uuid);
    Optional<User> findByResetPasswordToken_Uuid(UUID uuid);
    List<User> findAllByIsActiveTrue();
    void deleteByEmail(String email);
}

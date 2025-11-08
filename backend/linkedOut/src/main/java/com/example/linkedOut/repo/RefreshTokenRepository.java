package com.example.linkedOut.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.linkedOut.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(String userId);
}
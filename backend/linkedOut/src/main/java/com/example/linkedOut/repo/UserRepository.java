package com.example.linkedOut.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.linkedOut.model.User;


import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);


    List<User>  findByFullNameRegexOrHeadlineRegex(String namePattern, String headlinePattern);
}

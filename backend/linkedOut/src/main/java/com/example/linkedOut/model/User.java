package com.example.linkedOut.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Entity{
    @Id
    private String id;
    private String email;
    private String passwordHash;
    private String fullName;
    private String headline;
    private String about;
    private List<Experience> experiences = new ArrayList<>();
    private List<Education> educations = new ArrayList<>();
    private List<String> following = new ArrayList<>();
    private String avatarPath;
    private Instant createdAt;
    private Instant updatedAt;
}

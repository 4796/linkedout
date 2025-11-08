package com.example.linkedOut.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.example.linkedOut.model.Education;
import com.example.linkedOut.model.Experience;
import com.example.linkedOut.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto  implements GenericDto{
	private String id;
    private String email;
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

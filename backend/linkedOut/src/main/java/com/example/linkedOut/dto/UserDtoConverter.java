package com.example.linkedOut.dto;

import com.example.linkedOut.dto.UserDto;
import com.example.linkedOut.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter extends DtoConverter<User, UserDto> {
    
    @Override
    protected UserDto createDto() {
        return new UserDto();
    }
    
    @Override
    protected User createEntity() {
        return new User();
    }
    
    @Override
    protected void mapEntityToDto(User user, UserDto dto) {
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setHeadline(user.getHeadline());
        dto.setAbout(user.getAbout());
        dto.setExperiences(user.getExperiences());
        dto.setEducations(user.getEducations());
        dto.setFollowing(user.getFollowing());
        dto.setAvatarPath(user.getAvatarPath());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
    }
    
    @Override
    protected void mapDtoToEntity(UserDto dto, User user) {
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setHeadline(dto.getHeadline());
        user.setAbout(dto.getAbout());
        user.setExperiences(dto.getExperiences());
        user.setEducations(dto.getEducations());
        user.setFollowing(dto.getFollowing());
        user.setAvatarPath(dto.getAvatarPath());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
    }
}

package com.example.linkedOut.service;

import com.example.linkedOut.dto.UpdateUserDto;
import com.example.linkedOut.dto.UserDto;
import com.example.linkedOut.dto.UserDtoConverter;
import com.example.linkedOut.model.User;
import com.example.linkedOut.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    UserDtoConverter userConverter;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.max-size-mb}")
    private long maxSizeMb;
    
    
    public UserDto findById(String id) {
        return userConverter.toDto(userRepository.findById(id)
        		.orElseThrow(() -> new RuntimeException("User not found with id.")));
    }

    public UserDto updateProfile(String userId, UpdateUserDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Wrong id."));
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getHeadline() != null) user.setHeadline(dto.getHeadline());
        if (dto.getAbout() != null) user.setAbout(dto.getAbout());
        user.setUpdatedAt(Instant.now());
        return userConverter.toDto(userRepository.save(user));
        
    }

    public void follow(String userId, String targetId) {
        if (userId.equals(targetId)) throw new RuntimeException("Cannot follow yourself");
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getFollowing().contains(targetId)) {
            user.getFollowing().add(targetId);
            user.setUpdatedAt(Instant.now());
            userRepository.save(user);
        }
    }

    public void unfollow(String userId, String targetId) {
        User user = userRepository.findById(userId).orElseThrow();
        if (user.getFollowing().remove(targetId)) {
            user.setUpdatedAt(Instant.now());
            userRepository.save(user);
        }
    }

   
    public List<User> search(String q) {
        String pattern = "(?i).*" + Pattern.quote(q) + ".*";
        return userRepository.findByFullNameRegexOrHeadlineRegex(pattern, pattern);
    }

    
    public String uploadAvatar(String userId, MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new RuntimeException("Empty file");
        if (file.getSize() > maxSizeMb * 1024 * 1024) throw new RuntimeException("File too large");
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) throw new RuntimeException("Invalid file type");

        Path dir = Paths.get(uploadDir, "avatars");
        if (!Files.exists(dir)) Files.createDirectories(dir);

        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = userId + "-" + UUID.randomUUID() + (ext != null && !ext.isBlank() ? "." + ext : "");
        Path target = dir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        User user = userRepository.findById(userId).orElseThrow();
        user.setAvatarPath(Paths.get("avatars").resolve(filename).toString());
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
        return user.getAvatarPath();
    }
}

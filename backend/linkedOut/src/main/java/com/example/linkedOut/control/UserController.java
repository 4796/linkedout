package com.example.linkedOut.control;

import com.example.linkedOut.dto.UpdateUserDto;
import com.example.linkedOut.dto.UserDto;
import com.example.linkedOut.model.User;
import com.example.linkedOut.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //da
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    //da
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id,
                                       @RequestBody UpdateUserDto dto,
                                       Authentication auth) {
        requireOwner(auth, id);
        UserDto updated = userService.updateProfile(id, dto);
        return ResponseEntity.ok(updated);
    }
    
//da
    @PostMapping("/{id}/avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable String id,
                                          @RequestParam("file") MultipartFile file,
                                          Authentication auth) throws IOException {
        requireOwner(auth, id);
        String path = userService.uploadAvatar(id, file);
        return ResponseEntity.ok(Map.of("avatarPath", path));
    }

    @PostMapping("/{id}/follow/{targetId}")
    public ResponseEntity<?> follow(@PathVariable String id,
                                    @PathVariable String targetId,
                                    Authentication auth) {
        requireOwner(auth, id);
        userService.follow(id, targetId);
        return ResponseEntity.ok(Map.of("message", "followed"));
    }

    @PostMapping("/{id}/unfollow/{targetId}")
    public ResponseEntity<?> unfollow(@PathVariable String id,
                                      @PathVariable String targetId,
                                      Authentication auth) {
        requireOwner(auth, id);
        userService.unfollow(id, targetId);
        return ResponseEntity.ok(Map.of("message", "unfollowed"));
    }

    
    //da
    @GetMapping("/search")
    public ResponseEntity<List<User>> search(@RequestParam("q") String q) {
        return ResponseEntity.ok(userService.search(q));
    }

    
    //da
    private void requireOwner(Authentication auth, String pathUserId) {
        if (auth == null || auth.getPrincipal() == null) {
            throw new RuntimeException("Unauthorized");
        }
        String authUserId = auth.getPrincipal().toString();
        if (!authUserId.equals(pathUserId)) {
            throw new RuntimeException("Forbidden");
        }
    }
}

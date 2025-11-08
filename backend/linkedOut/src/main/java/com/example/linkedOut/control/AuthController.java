package com.example.linkedOut.control;

import com.example.linkedOut.dto.LoginDto;
import com.example.linkedOut.dto.RegisterDto;
import com.example.linkedOut.dto.TokenResponse;
import com.example.linkedOut.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
//da
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto) {
        authService.register(dto);
        return ResponseEntity.ok(Map.of("message", "registered"));
    }
//da
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginDto dto) {
        TokenResponse tokens = authService.login(dto);
        return ResponseEntity.ok(tokens);
    }
//da
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody Map<String, String> body) {
        String refresh = body.get("refreshToken");
        TokenResponse tokens = authService.refresh(refresh);
        return ResponseEntity.ok(tokens);
    }
//da
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        authService.logout(userId);
        return ResponseEntity.ok(Map.of("message", "logged out"));
    }
}
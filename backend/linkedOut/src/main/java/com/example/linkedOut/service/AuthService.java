package com.example.linkedOut.service;

import com.example.linkedOut.dto.LoginDto;
import com.example.linkedOut.dto.RegisterDto;
import com.example.linkedOut.dto.TokenResponse;
import com.example.linkedOut.exception.AuthNotUniqueException;
import com.example.linkedOut.exception.AuthWrongPasswordException;
import com.example.linkedOut.model.RefreshToken;
import com.example.linkedOut.model.User;
import com.example.linkedOut.repo.RefreshTokenRepository;
import com.example.linkedOut.repo.UserRepository;
import com.example.linkedOut.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;// = new BCryptPasswordEncoder();

    @Value("${jwt.refresh.expiration.days}")
    private long refreshExpDays;

    public void register(RegisterDto dto) {
        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new AuthNotUniqueException();
        });
        User user = User.builder()
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .headline(dto.getHeadline())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        userRepository.save(user);
    }

    public TokenResponse login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new AuthWrongPasswordException();
        }

        String access = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String refresh = createRefreshToken(user.getId());

        return new TokenResponse(access, jwtUtil.getAccessExpiresInSeconds(), refresh, user.getId());
    }

    private String createRefreshToken(String userId) {
        String token = UUID.randomUUID().toString();
        RefreshToken r = RefreshToken.builder()
                .userId(userId)
                .token(token)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(refreshExpDays * 24 * 3600))
                .build();
        refreshTokenRepository.save(r);
        return token;
    }

    public TokenResponse refresh(String refreshToken) {
        RefreshToken r = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (r.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.deleteById(r.getId());
            throw new RuntimeException("Refresh token expired");
        }
        User user = userRepository.findById(r.getUserId()).orElseThrow();
        String access = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        // (optional) rotate refresh token - here we reuse same token
        return new TokenResponse(access, jwtUtil.getAccessExpiresInSeconds(), refreshToken, user.getId());
    }

    public void logout(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}

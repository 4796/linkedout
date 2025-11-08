package com.example.linkedOut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse  implements GenericDto{
    private String accessToken;
    private long accessExpiresInSeconds;
    private String refreshToken;
    private String userId;
}

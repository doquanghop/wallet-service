package io.github.doquanghop.walletsystem.core.account.datatransferobject.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private String id;
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private final String tokenType = "Bearer";
    private LocalDateTime issuedAt;
    private long refreshTokenExpiresIn;
}
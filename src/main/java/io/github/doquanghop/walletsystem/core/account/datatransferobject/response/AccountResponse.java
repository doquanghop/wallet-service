package io.github.doquanghop.walletsystem.core.account.datatransferobject.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class AccountResponse {
    private String id;
    private String accessToken;
    private String refreshToken;
    private Date expiresIn;
    private final String tokenType = "Bearer";
    private LocalDateTime issuedAt;
    private Date refreshTokenExpiresIn;
}
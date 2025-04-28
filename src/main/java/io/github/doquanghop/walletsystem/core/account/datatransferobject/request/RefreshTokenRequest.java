package io.github.doquanghop.walletsystem.core.account.datatransferobject.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String accessToken;
    private String refreshToken;
}

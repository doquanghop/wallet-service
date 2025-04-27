package io.github.doquanghop.walletsystem.core.account.datatransferobject;

import java.util.Date;

public record TokenDTO(
        String accountId,
        String accessToken,
        Date accessTokenExpiry,
        String refreshToken,
        Date refreshTokenExpiry
) {
}
package io.github.doquanghop.walletsystem.core.account.service;

import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LoginRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LogoutRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RefreshTokenRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RegisterRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.response.AccountResponse;
import io.github.doquanghop.walletsystem.infrastructure.security.UserDetail;

public interface AccountService {
    void register(RegisterRequest request);

    AccountResponse login(LoginRequest request);

    void logout(LogoutRequest request);

    UserDetail authenticate(String accessToken);

    AccountResponse refreshToken(RefreshTokenRequest request);

    boolean isValidActiveAccount(String accountId);
}

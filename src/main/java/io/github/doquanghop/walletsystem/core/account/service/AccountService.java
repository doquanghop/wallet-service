package io.github.doquanghop.walletsystem.core.account.service;

import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LoginRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RegisterRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.response.UserResponse;

public interface AccountService {
    UserResponse register(RegisterRequest request);

    UserResponse login(LoginRequest request);
}

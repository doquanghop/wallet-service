package io.github.doquanghop.walletsystem.api;

import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LoginRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LogoutRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RefreshTokenRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RegisterRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.response.AccountResponse;
import io.github.doquanghop.walletsystem.core.account.service.AccountService;
import io.github.doquanghop.walletsystem.shared.types.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {
        accountService.register(request);
        return ApiResponse.success("Account created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountResponse>> login(@RequestBody LoginRequest request) {
        var response = accountService.login(request);
        return ApiResponse.success(response, "Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogoutRequest request) {
        accountService.logout(request);
        return ApiResponse.success("Logout successful");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AccountResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        var response = accountService.refreshToken(request);
        return ApiResponse.success(response, "Token refreshed successfully");
    }
}

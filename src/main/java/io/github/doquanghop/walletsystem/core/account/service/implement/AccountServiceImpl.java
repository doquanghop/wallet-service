package io.github.doquanghop.walletsystem.core.account.service.implement;


import com.nimbusds.jwt.JWTClaimsSet;
import io.github.doquanghop.walletsystem.core.account.component.JwtTokenProvider;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.TokenDTO;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.TokenMetadataDTO;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LoginRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LogoutRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RefreshTokenRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RegisterRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.response.AccountResponse;
import io.github.doquanghop.walletsystem.core.account.exception.AccountException;
import io.github.doquanghop.walletsystem.core.account.exception.TokenException;
import io.github.doquanghop.walletsystem.core.account.model.Account;
import io.github.doquanghop.walletsystem.core.account.repository.AccountRepository;
import io.github.doquanghop.walletsystem.core.account.service.AccountService;
import io.github.doquanghop.walletsystem.infrastructure.service.CacheService;
import io.github.doquanghop.walletsystem.infrastructure.security.UserDetail;
import io.github.doquanghop.walletsystem.shared.annotation.logging.ActionLog;
import io.github.doquanghop.walletsystem.shared.exceptions.AppException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final CacheService cacheService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Duration REGISTER_LOCK_TIMEOUT = Duration.ofSeconds(10);

    @Override
    @Transactional
    @ActionLog(message = "Registering new account")
    public void register(RegisterRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String phoneLockKey = "phoneNumber:lock:" + phoneNumber;

        boolean lockedPhoneNumber = cacheService.lock(phoneLockKey, REGISTER_LOCK_TIMEOUT);

        try {
            if (!lockedPhoneNumber || accountRepository.existsByPhone(phoneNumber)) {
                throw new AppException(AccountException.ACCOUNT_ALREADY_EXISTS, "PHONE_NUMBER");
            }

            String passwordHash = passwordEncoder.encode(request.getPassword());
            Account newAccount = Account.builder()
                    .fullName(request.getFullName())
                    .fullName(request.getFullName())
                    .phone(phoneNumber)
                    .passwordHash(passwordHash)
                    .build();
            accountRepository.save(newAccount);
        } finally {
            if (lockedPhoneNumber) {
                cacheService.unlock(phoneLockKey);
            }
        }
    }

    @Override
    @ActionLog(message = "Logging in account")
    public AccountResponse login(LoginRequest request) {
        Account existingAccount = accountRepository.findByPhone(request.getPhoneNumber())
                .orElseThrow(() -> new AppException(AccountException.ACCOUNT_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), existingAccount.getPasswordHash())) {
            throw new AppException(AccountException.INVALID_ACCOUNT_PAYLOAD);
        }
        String sessionId = UUID.randomUUID().toString();
        var newTokens = jwtTokenProvider.generateTokens(new TokenMetadataDTO(existingAccount.getId(), List.of("USER"), sessionId, new Date()));
        return this.buildAccountResponse(newTokens);
    }

    @Override
    @ActionLog(message = "Logging out account")
    public void logout(LogoutRequest request) throws AppException {
        if (!jwtTokenProvider.validateToken(request.getAccessToken())) {
            throw new AppException(AccountException.ACCOUNT_ACCESS_DENIED);
        }
        Date expirationDate = jwtTokenProvider.getExpirationFromToken(request.getAccessToken());
        Duration ttl = Duration.between(Instant.now(), expirationDate.toInstant());
        cacheService.setBlacklist(request.getAccessToken(), ttl);
    }

    @Override
    @ActionLog(logLevel = "DEBUG", message = "Authenticating user")
    public UserDetail authenticate(String accessToken) throws AppException {
        var claims = jwtTokenProvider.verifyToken(accessToken);
        if (cacheService.isBlacklisted(accessToken)) {
            throw new AppException(AccountException.ACCOUNT_ACCESS_DENIED);
        }
        return new UserDetail(
                claims.getSubject(),
                jwtTokenProvider.getRolesFromClaims(claims),
                jwtTokenProvider.getSessionIdFromClaims(claims)
        );
    }

    @Override
    @ActionLog(logLevel = "INFO", message = "Refreshing token")
    public AccountResponse refreshToken(RefreshTokenRequest request) {
        if (cacheService.isBlacklisted(request.getAccessToken())) {
            throw new AppException(TokenException.TOKEN_BLACKLISTED);
        }
        JWTClaimsSet claims = jwtTokenProvider.verifyToken(request.getRefreshToken());
        String accountId = claims.getSubject();
        Date accessTokenExpiration = jwtTokenProvider.getExpirationFromToken(request.getAccessToken());
        if (accessTokenExpiration != null && accessTokenExpiration.after(new Date())) {
            Duration ttl = Duration.between(Instant.now(), accessTokenExpiration.toInstant());
            cacheService.setBlacklist(request.getAccessToken(), ttl);
        }
        var newTokens = jwtTokenProvider.refreshToken(request.getRefreshToken(), request.getAccessToken());
        return this.buildAccountResponse(newTokens);
    }

    @Override
    @ActionLog(logLevel = "DEBUG", message = "Checking account validity")
    public boolean isValidActiveAccount(String accountId) {
        return accountRepository.findById(accountId)
                .isPresent();
    }

    private AccountResponse buildAccountResponse(TokenDTO tokenDTO) {
        return AccountResponse.builder()
                .id(tokenDTO.accountId())
                .accessToken(tokenDTO.accessToken())
                .expiresIn(tokenDTO.accessTokenExpiry())
                .refreshToken(tokenDTO.refreshToken())
                .refreshTokenExpiresIn(tokenDTO.refreshTokenExpiry())
                .build();
    }
}

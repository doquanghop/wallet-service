package io.github.doquanghop.walletsystem.core.account.service.implement;

import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.LoginRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.request.RegisterRequest;
import io.github.doquanghop.walletsystem.core.account.datatransferobject.response.UserResponse;
import io.github.doquanghop.walletsystem.core.account.model.Account;
import io.github.doquanghop.walletsystem.core.account.repository.AccountRepository;
import io.github.doquanghop.walletsystem.core.account.service.AccountService;
import io.github.doquanghop.walletsystem.core.account.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Override
    public UserResponse register(RegisterRequest request) {
        String passwordHash = passwordEncoder.encode(request.getPassword());
        Account newAccount = Account.builder()
                .phone(request.getPhoneNumber())
                .passwordHash(passwordHash)
                .status(UserStatus.ACTIVE)
                .build();
        accountRepository.save(newAccount);
        return UserResponse.builder().build();
    }

    @Override
    public UserResponse login(LoginRequest request) {
        Account account = accountRepository.findByPhone(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return null;
    }
}

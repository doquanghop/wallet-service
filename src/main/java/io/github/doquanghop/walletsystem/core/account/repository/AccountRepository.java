package io.github.doquanghop.walletsystem.core.account.repository;

import io.github.doquanghop.walletsystem.core.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByPhone(String phone);
}

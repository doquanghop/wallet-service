package io.github.doquanghop.walletsystem.core.wallet.repository;

import io.github.doquanghop.walletsystem.core.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}

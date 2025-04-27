package io.github.doquanghop.walletsystem.core.deposit.repository;

import io.github.doquanghop.walletsystem.core.deposit.model.DepositRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<DepositRequest, String> {
}

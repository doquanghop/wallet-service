package io.github.doquanghop.walletsystem.core.withdraw.repository;

import io.github.doquanghop.walletsystem.core.withdraw.model.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<WithdrawRequest, String> {
}

package io.github.doquanghop.walletsystem.core.transaction.repository;

import io.github.doquanghop.walletsystem.core.transaction.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, String> {
}

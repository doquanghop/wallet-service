package io.github.doquanghop.walletsystem.core.transaction.repository;

import io.github.doquanghop.walletsystem.core.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}

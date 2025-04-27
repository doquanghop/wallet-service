package io.github.doquanghop.walletsystem.core.transaction.service.implement;

import io.github.doquanghop.walletsystem.core.transaction.repository.TransactionRepository;
import io.github.doquanghop.walletsystem.core.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
}

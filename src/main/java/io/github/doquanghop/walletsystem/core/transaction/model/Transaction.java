package io.github.doquanghop.walletsystem.core.transaction.model;

import io.github.doquanghop.walletsystem.core.transaction.type.TransactionStatus;
import io.github.doquanghop.walletsystem.shared.entity.AuditableEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @Column(name = "from_wallet_id")
    private String fromWalletId;

    @Column(name = "to_wallet_id")
    private String toWalletId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.PENDING;
}
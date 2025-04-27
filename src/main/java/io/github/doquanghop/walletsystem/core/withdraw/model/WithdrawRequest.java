package io.github.doquanghop.walletsystem.core.withdraw.model;

import io.github.doquanghop.walletsystem.core.withdraw.type.WithdrawRequestStatus;
import io.github.doquanghop.walletsystem.shared.entity.AuditableEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "withdraw_requests")
public class WithdrawRequest extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "wallet_id", nullable = false)
    private String walletId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    private String bankAccountNumber;

    @Column(nullable = false, length = 100)
    private String bankName;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private WithdrawRequestStatus status = WithdrawRequestStatus.PENDING;
}
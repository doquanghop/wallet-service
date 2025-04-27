package io.github.doquanghop.walletsystem.core.wallet.model;

import io.github.doquanghop.walletsystem.core.wallet.type.WalletStatus;
import io.github.doquanghop.walletsystem.shared.entity.AuditableEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
public class Wallet extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "locked_balance", precision = 18, scale = 2)
    private BigDecimal lockedBalance = BigDecimal.ZERO;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private WalletStatus status = WalletStatus.ACTIVE;
}
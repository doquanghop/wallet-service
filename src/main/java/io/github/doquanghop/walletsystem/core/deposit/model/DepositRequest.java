package io.github.doquanghop.walletsystem.core.deposit.model;

import io.github.doquanghop.walletsystem.core.deposit.type.DepositRequestStatus;
import io.github.doquanghop.walletsystem.shared.entity.AuditableEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "deposit_requests")
public class DepositRequest extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "wallet_id", nullable = false)
    private String walletId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(length = 50)
    private String provider;

    @Column(name = "provider_ref_id", length = 100)
    private String providerRefId;

    @Column(nullable = false, length = 20)
    private DepositRequestStatus status = DepositRequestStatus.PENDING;
}
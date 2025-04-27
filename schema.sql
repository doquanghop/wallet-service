CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50) UNIQUE NOT NULL,
    email      VARCHAR(100) UNIQUE,
    phone      VARCHAR(20) UNIQUE,
    status     VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE wallets
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id        BIGINT NOT NULL,
    balance        DECIMAL(18, 2) DEFAULT 0.00, -- Tiền trong ví
    locked_balance DECIMAL(18, 2) DEFAULT 0.00, -- Tiền tạm giữ
    status         VARCHAR(20)    DEFAULT 'ACTIVE',
    created_at     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE transactions
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    transaction_id VARCHAR(100) UNIQUE NOT NULL,
    from_wallet_id BIGINT,                        -- NULL nếu là nạp tiền
    to_wallet_id   BIGINT,                        -- NULL nếu là rút tiền
    amount         DECIMAL(18, 2)      NOT NULL,
    type           VARCHAR(20)         NOT NULL,  -- DEPOSIT, WITHDRAW, TRANSFER
    status         VARCHAR(20) DEFAULT 'PENDING', -- PENDING, SUCCESS, FAILED
    description    TEXT,
    created_at     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (from_wallet_id) REFERENCES wallets (id),
    FOREIGN KEY (to_wallet_id) REFERENCES wallets (id)
);

CREATE TABLE transaction_logs
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    transaction_id BIGINT NOT NULL,
    action         VARCHAR(50), -- INIT, PROCESS, SUCCESS, FAIL
    message        TEXT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES transactions (id)
);

CREATE TABLE deposit_requests
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallet_id       BIGINT         NOT NULL,
    amount          DECIMAL(18, 2) NOT NULL,
    provider        VARCHAR(50),                   -- VNPay, MoMo, ZaloPay, etc.
    provider_ref_id VARCHAR(100),                  -- Mã giao dịch của VNPay/MoMo
    status          VARCHAR(20) DEFAULT 'PENDING', -- PENDING, SUCCESS, FAILED
    created_at      TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (wallet_id) REFERENCES wallets (id)
);

CREATE TABLE withdraw_requests
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallet_id           BIGINT         NOT NULL,
    amount              DECIMAL(18, 2) NOT NULL,
    bank_account_number VARCHAR(50)    NOT NULL,
    bank_name           VARCHAR(100)   NOT NULL,
    status              VARCHAR(20) DEFAULT 'PENDING', -- PENDING, SUCCESS, FAILED
    created_at          TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (wallet_id) REFERENCES wallets (id)
);


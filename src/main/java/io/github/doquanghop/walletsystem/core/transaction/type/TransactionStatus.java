package io.github.doquanghop.walletsystem.core.transaction.type;

public enum TransactionStatus {
    PENDING,
    SUCCESS,
    FAILED,
    CANCELED,
    REFUNDED;

    public boolean canTransitionTo(TransactionStatus targetStatus) {
        return switch (this) {
            case PENDING -> targetStatus == SUCCESS || targetStatus == FAILED || targetStatus == CANCELED;
            case SUCCESS, FAILED -> targetStatus == CANCELED;
//            case CANCELED -> false;
            default -> false;
        };
    }
}

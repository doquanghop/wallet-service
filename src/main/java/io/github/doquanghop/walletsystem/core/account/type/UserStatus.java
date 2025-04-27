package io.github.doquanghop.walletsystem.core.account.type;

public enum UserStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    DELETED;

    public boolean canTransitionTo(UserStatus targetStatus) {
        return switch (this) {
            case ACTIVE -> targetStatus == INACTIVE || targetStatus == SUSPENDED;
            case INACTIVE -> targetStatus == ACTIVE || targetStatus == SUSPENDED;
            case SUSPENDED -> targetStatus == ACTIVE || targetStatus == DELETED;
            default -> false;
        };
    }
}

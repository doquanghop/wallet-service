package io.github.doquanghop.walletsystem.core.wallet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum WalletException implements ExceptionCode {
    INSUFFICIENT_BALANCE(4100, "INSUFFICIENT_BALANCE", HttpStatus.BAD_REQUEST), // 400
    INVALID_WALLET_ID(4101, "INVALID_WALLET_ID", HttpStatus.BAD_REQUEST), // 400
    WALLET_NOT_FOUND(4104, "WALLET_NOT_FOUND", HttpStatus.NOT_FOUND), // 404
    WALLET_CREATION_FAILED(4105, "WALLET_CREATION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    WALLET_UPDATE_FAILED(4106, "WALLET_UPDATE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    WALLET_DELETION_FAILED(4107, "WALLET_DELETION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR); // 500

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}
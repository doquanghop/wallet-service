package io.github.doquanghop.walletsystem.core.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum TransactionException implements ExceptionCode {
    INVALID_TRANSACTION_ID(4201, "INVALID_TRANSACTION_ID", HttpStatus.BAD_REQUEST), // 400
    TRANSACTION_NOT_FOUND(4204, "TRANSACTION_NOT_FOUND", HttpStatus.NOT_FOUND), // 404
    TRANSACTION_CREATION_FAILED(4205, "TRANSACTION_CREATION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    TRANSACTION_UPDATE_FAILED(4206, "TRANSACTION_UPDATE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    TRANSACTION_PROCESS_FAILED(4207, "TRANSACTION_PROCESS_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    TRANSACTION_ALREADY_EXISTS(4208, "TRANSACTION_ALREADY_EXISTS", HttpStatus.CONFLICT), // 409
    INVALID_TRANSACTION_STATUS(4209, "INVALID_TRANSACTION_STATUS", HttpStatus.BAD_REQUEST); // 400

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}
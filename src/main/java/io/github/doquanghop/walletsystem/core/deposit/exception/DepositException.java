package io.github.doquanghop.walletsystem.core.deposit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum DepositException implements ExceptionCode {
    DEPOSIT_FAILED(4400, "DEPOSIT_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    DEPOSIT_NOT_FOUND(4401, "DEPOSIT_NOT_FOUND", HttpStatus.NOT_FOUND), // 404
    INVALID_DEPOSIT_AMOUNT(4402, "INVALID_DEPOSIT_AMOUNT", HttpStatus.BAD_REQUEST), // 400
    DEPOSIT_ALREADY_EXISTS(4403, "DEPOSIT_ALREADY_EXISTS", HttpStatus.CONFLICT); // 409

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}
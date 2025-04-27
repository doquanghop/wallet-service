package io.github.doquanghop.walletsystem.core.withdraw.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum WithdrawException implements ExceptionCode {
    WITHDRAW_FAILED(4500, "WITHDRAW_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    WITHDRAW_NOT_FOUND(4501, "WITHDRAW_NOT_FOUND", HttpStatus.NOT_FOUND), // 404
    INVALID_WITHDRAW_AMOUNT(4502, "INVALID_WITHDRAW_AMOUNT", HttpStatus.BAD_REQUEST), // 400
    WITHDRAW_ALREADY_EXISTS(4503, "WITHDRAW_ALREADY_EXISTS", HttpStatus.CONFLICT); // 409

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}
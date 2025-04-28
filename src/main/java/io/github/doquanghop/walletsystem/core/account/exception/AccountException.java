package io.github.doquanghop.walletsystem.core.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum AccountException implements ExceptionCode {
    ACCOUNT_NOT_FOUND(4304, "ACCOUNT_NOT_FOUND", HttpStatus.NOT_FOUND), // 404
    ACCOUNT_ALREADY_EXISTS(4309, "ACCOUNT_ALREADY_EXISTS", HttpStatus.CONFLICT), // 409
    INVALID_ACCOUNT_ID(4300, "INVALID_ACCOUNT_ID", HttpStatus.BAD_REQUEST), // 400
    ACCOUNT_CREATION_FAILED(4305, "ACCOUNT_CREATION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    ACCOUNT_UPDATE_FAILED(4306, "ACCOUNT_UPDATE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    ACCOUNT_DELETION_FAILED(4307, "ACCOUNT_DELETION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    ACCOUNT_AUTHENTICATION_FAILED(4310, "ACCOUNT_AUTHENTICATION_FAILED", HttpStatus.FORBIDDEN), // 403
    INVALID_ACCOUNT_PAYLOAD(4301, "INVALID_ACCOUNT_PAYLOAD", HttpStatus.BAD_REQUEST), // 400
    ACCOUNT_ACCESS_DENIED(4311, "ACCOUNT_ACCESS_DENIED", HttpStatus.FORBIDDEN); // 403

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}
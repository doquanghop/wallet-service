package io.github.doquanghop.walletsystem.core.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum UserException implements ExceptionCode {
    USER_NOT_FOUND(4304, "USER_NOT_FOUND", HttpStatus.NOT_FOUND), // 404
    USER_ALREADY_EXISTS(4309, "USER_ALREADY_EXISTS", HttpStatus.CONFLICT), // 409
    INVALID_USER_ID(4300, "INVALID_USER_ID", HttpStatus.BAD_REQUEST), // 400
    USER_CREATION_FAILED(4305, "USER_CREATION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    USER_UPDATE_FAILED(4306, "USER_UPDATE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    USER_DELETION_FAILED(4307, "USER_DELETION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    USER_AUTHENTICATION_FAILED(4310, "USER_AUTHENTICATION_FAILED", HttpStatus.FORBIDDEN), // 403
    INVALID_USER_PAYLOAD(4301, "INVALID_USER_PAYLOAD", HttpStatus.BAD_REQUEST), // 400
    USER_ACCESS_DENIED(4311, "USER_ACCESS_DENIED", HttpStatus.FORBIDDEN); // 403

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}
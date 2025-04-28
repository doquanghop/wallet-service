package io.github.doquanghop.walletsystem.core.account.exception;

import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum TokenException implements ExceptionCode {
    INVALID_TOKEN(4310, "INVALID_TOKEN", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(4311, "EXPIRED_TOKEN", HttpStatus.UNAUTHORIZED),
    TOKEN_BLACKLISTED(4312, "TOKEN_BLACKLISTED", HttpStatus.UNAUTHORIZED),
    UNEXPECTED_TOKEN_ERROR(4313, "UNEXPECTED_TOKEN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_SESSION_ID(4314, "INVALID_SESSION_ID", HttpStatus.UNAUTHORIZED);

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}

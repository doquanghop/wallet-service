package io.github.doquanghop.walletsystem.shared.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum ResourceException implements ExceptionCode {
    ENTITY_NOT_FOUND(4004, "ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND), // 404
    ENTITY_ALREADY_EXISTS(4009, "ENTITY_ALREADY_EXISTS", HttpStatus.CONFLICT), // 409
    INVALID_ENTITY_ID(4000, "INVALID_ENTITY_ID", HttpStatus.BAD_REQUEST), // 400
    CREATION_FAILED(5000, "CREATION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    UPDATE_FAILED(5001, "UPDATE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    DELETE_FAILED(5002, "DELETE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    ACCESS_DENIED(4003, "ACCESS_DENIED", HttpStatus.FORBIDDEN), // 403
    INVALID_PAYLOAD(4001, "INVALID_PAYLOAD", HttpStatus.BAD_REQUEST), // 400
    UNEXPECTED_ERROR(5999, "UNEXPECTED_ERROR", HttpStatus.INTERNAL_SERVER_ERROR), // 500
    INTERNAL_SERVER_ERROR(5000, "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR); // 500

    private final Integer code;
    private final String type;
    private final HttpStatus httpStatus;
}

package io.github.doquanghop.walletsystem.shared.exceptions;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private String message;
    private final ExceptionCode exceptionCode;

    public AppException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public AppException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

}

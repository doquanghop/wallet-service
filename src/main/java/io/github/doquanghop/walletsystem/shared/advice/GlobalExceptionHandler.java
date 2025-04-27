package io.github.doquanghop.walletsystem.shared.advice;


import io.github.doquanghop.walletsystem.shared.exceptions.AppException;
import io.github.doquanghop.walletsystem.shared.exceptions.ExceptionCode;
import io.github.doquanghop.walletsystem.shared.annotation.logging.ActionLog;
import io.github.doquanghop.walletsystem.shared.types.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ActionLog
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        return handleException(e.getExceptionCode(), e.getMessage());
    }

    private ResponseEntity<ApiResponse<?>> handleException(ExceptionCode e, String message) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("type", e != null ? e.getType() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        HttpStatus status = e != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<?> errorResponse = ApiResponse.<Void>build()
                .withHttpStatus(e != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR)
                .withCode(e != null ? e.getCode() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withErrors(errors)
                .withMessage(message != null ? message : "");
        return ResponseEntity.status(status).body(errorResponse);
    }
}

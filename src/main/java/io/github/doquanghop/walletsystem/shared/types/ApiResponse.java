package io.github.doquanghop.walletsystem.shared.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ApiResponse<T> {
    @JsonIgnore
    private HttpStatus httpStatus;
    @JsonIgnore
    private HttpHeaders headers;
    private int code;
    private Boolean isSuccess;
    private String message;
    private T data;
    private Map<String, Object> errors;
    private Date timestamp;

    public ApiResponse() {
        httpStatus = HttpStatus.OK;
        code = 2000;
        isSuccess = true;
        errors = new HashMap<>();
        timestamp = new Date();
    }

    public static <T> ApiResponse<T> build() {
        return new ApiResponse<>();
    }

    public ApiResponse<T> withCode(int code) {
        this.code = code;
        return this;
    }

    public ApiResponse<T> withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ApiResponse<T> withData(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> withHttpHeaders(HttpHeaders headers) {
        this.headers = headers;
        this.code = httpStatus.value();
        this.isSuccess = httpStatus.is2xxSuccessful();
        return this;
    }

    public ApiResponse<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> withErrors(Map<String, Object> errors) {
        this.errors = errors;
        this.isSuccess = errors == null || errors.isEmpty();
        return this;
    }

    public ResponseEntity<ApiResponse<T>> toEntity() {
        return ResponseEntity.status(httpStatus != null ? httpStatus : HttpStatus.OK)
                .headers(headers != null ? headers : new HttpHeaders())
                .body(this);
    }

    public static ResponseEntity<ApiResponse<Void>> success(String message) {
        return buildSuccessResponse(null, message != null ? message : "Success");
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return buildSuccessResponse(data, message);
    }

    private static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse(T data, String message) {
        return ApiResponse.<T>build()
                .withData(data)
                .withMessage(message != null ? message : "Success")
                .toEntity();
    }
}

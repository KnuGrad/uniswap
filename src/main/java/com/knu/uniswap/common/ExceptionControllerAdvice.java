package com.knu.uniswap.common;

import com.knu.uniswap.common.exception.DuplicationException;
import com.knu.uniswap.common.exception.EmailNotCertifiedException;
import com.knu.uniswap.common.exception.ValidationException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> validate(ValidationException e) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(400, e.getMessage(), e.getErrorMap()));
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> duplicate(DuplicationException e) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(400, e.getMessage(), null));
    }

    @ExceptionHandler(EmailNotCertifiedException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> notCertified(
        EmailNotCertifiedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(403, e.getMessage(), null));
    }

}

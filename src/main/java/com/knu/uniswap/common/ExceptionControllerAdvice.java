package com.knu.uniswap.common;

import com.knu.uniswap.common.exception.ValidationException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> validation(ValidationException e) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(400, e.getMessage(), e.getErrorMap()));
    }

}

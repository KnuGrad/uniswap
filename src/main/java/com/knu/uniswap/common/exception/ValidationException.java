package com.knu.uniswap.common.exception;

import java.util.Map;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private Map<String, String> errorMap;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Map errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

}

package com.knu.uniswap.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    // 성공 응답
    public static <T> ApiResponse<T> success(int code, T data) {
        return new ApiResponse<>(code, "success", data);
    }

    // 실패 응답
    public static <T> ApiResponse<T> fail(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

}

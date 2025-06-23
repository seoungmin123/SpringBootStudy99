package com.example.SpringBootStudy99.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Integer status;

    // 성공 응답을 생성하는 정적 메서드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "요청이 성공했습니다.", data, 200);
    }

    // 실패 응답을 생성하는 정적 메서드
    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(false, message, null, status);
    }

}

package com.codeconnect.common.exeption;

import com.codeconnect.common.exeption.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 내장된 AOP (다른 라이브러리 없이 사용 가능한 AOP)
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class) // RuntimeException 처리
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        var responseBody = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) // 500 상태 코드
                .code("RUNTIME_EXCEPTION")
                .message(exception.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseBody);
    }
}

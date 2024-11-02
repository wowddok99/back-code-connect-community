package com.example.codeconvoproject.dto;

public record ResponseDto<T>(
        Status status,
        String msg,
        T data
) {
    public enum Status {
        SUCCESS, FAILURE
    }
}

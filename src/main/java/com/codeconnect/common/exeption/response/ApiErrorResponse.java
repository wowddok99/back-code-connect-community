package com.codeconnect.common.exeption.response;

import lombok.Builder;

@Builder
public record ApiErrorResponse(
        int status,
        String code,
        String message
) {}

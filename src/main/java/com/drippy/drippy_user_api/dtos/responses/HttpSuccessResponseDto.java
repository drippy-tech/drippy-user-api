package com.drippy.drippy_user_api.dtos.responses;

public record HttpSuccessResponseDto<T>(
        T data,
        String message
) {}

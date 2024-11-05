package com.drippy.drippy_user_api.dtos.responses;

import java.time.OffsetDateTime;
import java.util.List;

public record HttpErrorResponseDto(
    List<String> messages,
    OffsetDateTime timestamp
) {}

package com.drippy.drippy_user_api.dtos.responses;

import com.drippy.drippy_user_api.enums.Profile;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        String username,
        Profile profile,
        Boolean status,
        Boolean isEmailVerified,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}

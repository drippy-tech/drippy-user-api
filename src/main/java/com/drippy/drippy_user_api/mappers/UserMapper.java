package com.drippy.drippy_user_api.mappers;

import com.drippy.drippy_user_api.dtos.requests.UserRequestDto;
import com.drippy.drippy_user_api.dtos.responses.UserResponseDto;
import com.drippy.drippy_user_api.enums.Profile;
import com.drippy.drippy_user_api.models.User;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class UserMapper {
    public User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setUsername(dto.username());
        user.setProfile(Profile.valueOf(dto.profile().toUpperCase()));
        user.setStatus(true);
        user.setIsEmailVerified(false);
        user.setCreatedAt(OffsetDateTime.now());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getProfile(),
                user.getStatus(),
                user.getIsEmailVerified(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

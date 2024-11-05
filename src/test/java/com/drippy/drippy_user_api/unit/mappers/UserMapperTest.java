package com.drippy.drippy_user_api.unit.mappers;

import com.drippy.drippy_user_api.dtos.requests.UserRequestDto;
import com.drippy.drippy_user_api.dtos.responses.UserResponseDto;
import com.drippy.drippy_user_api.enums.Profile;
import com.drippy.drippy_user_api.mappers.UserMapper;
import com.drippy.drippy_user_api.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("Should map UserRequestDto to User entity")
    void shouldMapUserRequestDtoToUserEntity() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                "Jhon Doe",
                "jhon.doe@mail.com",
                "password123",
                "password123",
                "jhon_doe",
                "USER"

        );

        //Act
        User user = userMapper.toEntity(userRequestDto);

        //Assert
        assertNotNull(user.getId());
        assertEquals(userRequestDto.name(), user.getName());
        assertEquals(userRequestDto.email(), user.getEmail());
        assertEquals(userRequestDto.password(), user.getPassword());
        assertEquals(userRequestDto.username(), user.getUsername());
        assertEquals(Profile.USER, user.getProfile());
        assertEquals(true, user.getStatus());
        assertEquals(false, user.getIsEmailVerified());
        assertNotNull(user.getCreatedAt());
    }

    @Test
    @DisplayName("Should map User entity to UserResponseDto")
    void shouldMapUserEntityToUserResponseDto() {
        // Arrange
        UUID id = UUID.randomUUID();
        OffsetDateTime createdAt = OffsetDateTime.now();

        User user = new User();
        user.setId(id);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setProfile(Profile.USER);
        user.setStatus(true);
        user.setIsEmailVerified(false);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(null);

        // Act
        UserResponseDto responseDto = userMapper.toResponseDto(user);

        // Assert
        assertEquals(user.getId(), responseDto.id());
        assertEquals(user.getName(), responseDto.name());
        assertEquals(user.getEmail(), responseDto.email());
        assertEquals(user.getUsername(), responseDto.username());
        assertEquals(user.getProfile(), responseDto.profile());
        assertEquals(user.getStatus(), responseDto.status());
        assertEquals(user.getIsEmailVerified(), responseDto.isEmailVerified());
        assertEquals(user.getCreatedAt(), responseDto.createdAt());
        assertEquals(user.getUpdatedAt(), responseDto.updatedAt());
    }
}
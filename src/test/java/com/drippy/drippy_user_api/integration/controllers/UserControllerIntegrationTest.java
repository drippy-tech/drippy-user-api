package com.drippy.drippy_user_api.integration.controllers;

import com.drippy.drippy_user_api.dtos.requests.UserRequestDto;
import com.drippy.drippy_user_api.dtos.responses.HttpErrorResponseDto;
import com.drippy.drippy_user_api.dtos.responses.HttpSuccessResponseDto;
import com.drippy.drippy_user_api.dtos.responses.UserResponseDto;
import com.drippy.drippy_user_api.integration.BaseIntegrationTest;
import com.drippy.drippy_user_api.models.User;
import com.drippy.drippy_user_api.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return status 400 when required fields are not provided")
    void shouldReturnStatus400WhenRequiredFieldsAreNotProvided() {
        //Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                null,
                null,
                null,
                null,
                null,
                null
        );


        //Act
        ResponseEntity<HttpErrorResponseDto> response = restTemplate.postForEntity(
                "/users",
                userRequestDto,
                HttpErrorResponseDto.class
        );

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        HttpErrorResponseDto errorResponse = response.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.messages()).containsExactlyInAnyOrder(
                "name: is required",
                "password: is required",
                "passwordConfirmation: is required",
                "email: is required",
                "username: is required",
                "profile: is required"
        );
        assertThat(errorResponse.messages()).hasSize(6);
    }

    @Test
    @DisplayName("Should return status 400 when are not passed a valid email")
    void shouldReturn400WhenAreNotPassedValidEmail() {
        //Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                "Jon Doe",
                "jon.doe",
                "12345678",
                "12345678",
                "jondoe",
                "USER"
        );

        //Act
        ResponseEntity<HttpErrorResponseDto> response = restTemplate.postForEntity(
                "/users",
                userRequestDto,
                HttpErrorResponseDto.class
        );

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        HttpErrorResponseDto errorResponse = response.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.messages()).containsExactly("email: must be a valid email");
    }

    @Test
    @DisplayName("Should return status 400 when are not passed correct length for params")
    void shouldReturn400WhenAreNotPassedCorrectParamsLength() {
        //Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                "Jo",
                "a".repeat(91) + "@gmail.com",
                "12345",
                "12345",
                "jo",
                "USER"
        );

        //Act
        ResponseEntity<HttpErrorResponseDto> response = restTemplate.postForEntity(
                "/users",
                userRequestDto,
                HttpErrorResponseDto.class
        );

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        HttpErrorResponseDto errorResponse = response.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.messages()).containsExactlyInAnyOrder(
                "password: must be between 8 and 64 characters",
                "username: must be between 3 and 16 characters",
                "name: must be between 3 and 80 characters",
                "email: must have at max 100 characters",
                "email: must be a valid email"
        );
    }

    @Test
    @DisplayName("Should return status 400 when password and passwordConfirmation are different")
    void shouldReturn400WhenPasswordMismatch() {
        //Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                "Jon Doe",
                "jon.doe@gmail.com",
                "12345678",
                "123456789",
                "jondoe",
                "USER"
        );

        //Act
        ResponseEntity<HttpErrorResponseDto> response = restTemplate.postForEntity(
                "/users",
                userRequestDto,
                HttpErrorResponseDto.class
        );

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        HttpErrorResponseDto errorResponse = response.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.messages()).containsExactly("password and confirmation password do not match");
    }

    @Test
    @DisplayName("Should return status 400 when are passed a different ENUM for profile")
    void shouldReturn400WhenArePassedDifferentEnumValue() {
        //Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                "Jon Doe",
                "jon.doe@gmail.com",
                "123456789",
                "123456789",
                "jondoe",
                "DIFFERENT_PROFILE"
        );

        //Act
        ResponseEntity<HttpErrorResponseDto> response = restTemplate.postForEntity(
                "/users",
                userRequestDto,
                HttpErrorResponseDto.class
        );

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        HttpErrorResponseDto errorResponse = response.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.messages()).containsExactly("profile: must be ADMIN or USER");
    }

    @Test
    @DisplayName("Should return 201 when passed all valid fields and create a new user")
    void shouldReturn201WhenPassedAllValidFields() {
        UserRequestDto userRequestDTO = new UserRequestDto(
                "Jon Doe",
                "jon.doe@gmail.com",
                "123456789",
                "123456789",
                "jondoe",
                "ADMIN"
        );

        ResponseEntity<HttpSuccessResponseDto<UserResponseDto>> response = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                new HttpEntity<UserRequestDto>(userRequestDTO),
                new ParameterizedTypeReference<HttpSuccessResponseDto<UserResponseDto>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        HttpSuccessResponseDto<UserResponseDto> successResponse = response.getBody();
        UUID userId = successResponse.data().id();

        assertThat(successResponse).isNotNull();
        assertThat(successResponse.message()).isEqualTo("User created successfully");
        assertThat(userId).isNotNull();

        User user = userRepository.findById(userId).orElse(null);
        assertThat(user).isNotNull();
    }
}

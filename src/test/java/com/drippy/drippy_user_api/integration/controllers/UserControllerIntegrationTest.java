package com.drippy.drippy_user_api.integration.controllers;

import com.drippy.drippy_user_api.dtos.requests.UserRequestDto;
import com.drippy.drippy_user_api.dtos.responses.HttpErrorResponseDto;
import com.drippy.drippy_user_api.integration.BaseIntegrationTest;
import com.drippy.drippy_user_api.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    }
}

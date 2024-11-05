package com.drippy.drippy_user_api.controllers;

import com.drippy.drippy_user_api.dtos.requests.UserRequestDto;
import com.drippy.drippy_user_api.dtos.responses.HttpSuccessResponseDto;
import com.drippy.drippy_user_api.dtos.responses.UserResponseDto;
import com.drippy.drippy_user_api.exceptions.customs.MismatchPasswordException;
import com.drippy.drippy_user_api.mappers.UserMapper;
import com.drippy.drippy_user_api.models.User;
import com.drippy.drippy_user_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<HttpSuccessResponseDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto dto) {
        if(!dto.password().equals(dto.passwordConfirmation()))
            throw new MismatchPasswordException();

        User createdUser = userService.createUser(userMapper.toEntity(dto));
        HttpSuccessResponseDto<UserResponseDto> successResponseDto = new HttpSuccessResponseDto<>(
                userMapper.toResponseDto(createdUser),
                "User created successfully"
        );

        return new ResponseEntity<>(successResponseDto, HttpStatus.CREATED);
    }
}

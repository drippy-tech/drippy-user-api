package com.drippy.drippy_user_api.exceptions;

import com.drippy.drippy_user_api.dtos.responses.HttpErrorResponseDto;
import com.drippy.drippy_user_api.exceptions.customs.MismatchPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponseDto> handleUnhandledException(HttpServletRequest req, Exception ex) {
        log.error("Unexpected error occurred on request: {} Method: {}", req.getServletPath(), req.getMethod(), ex);
        HttpErrorResponseDto httpErrorResponseDto = new HttpErrorResponseDto(
                List.of("Internal server error"),
                OffsetDateTime.now()
        );
        return new ResponseEntity<>(httpErrorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpErrorResponseDto> handleValidationException(
            HttpServletRequest req, MethodArgumentNotValidException ex
    ) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        HttpErrorResponseDto errorDtoResponse = new HttpErrorResponseDto(
                errors,
                OffsetDateTime.now()
        );

        return new ResponseEntity<>(errorDtoResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MismatchPasswordException.class)
    public ResponseEntity<HttpErrorResponseDto> handleMismatchPasswordException(
            HttpServletRequest req, MismatchPasswordException ex
    ) {
        HttpErrorResponseDto httpErrorResponseDTO = new HttpErrorResponseDto(
                List.of(ex.getMessage()),
                OffsetDateTime.now()
        );
        return new ResponseEntity<>(httpErrorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}

package com.drippy.drippy_user_api.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
   @NotNull(message = "is required")
   @Size(min = 3, max = 80, message = "must be between 3 and 80 characters")
   String name,

   @NotNull(message = "is required")
   @Email(message = "must be a valid email")
   @Size(max = 100, message = "must be between 3 and 100 characters")
   String email,

   @NotNull(message = "is reuquired")
   @Size(min = 8, max = 64, message = "must be between 8 and 64 characters")
   String password,

   @NotNull(message = "is required")
   String passwordConfirmation,

   @NotNull(message = "is required")
   @Size(min = 3, max = 16, message = "must be between 3 and 16 characters")
   String username,

   @NotNull(message = "is required")
   @Pattern(regexp = "^(ADMIN|USER)$", message = "must be ADMIN or USER")
   String profile
) {}

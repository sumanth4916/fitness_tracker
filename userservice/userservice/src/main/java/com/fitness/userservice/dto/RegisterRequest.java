package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "not blank")
    private String firstName;
    @NotBlank(message = "not blank")
    private String lastName;
    @Email(message = "Invalid mail")
    private String email;
    @Size(min = 6,message = "min 6 chars required")
    private String password;
}

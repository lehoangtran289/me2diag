package com.hust.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignupRequestDTO {
    @Size(max = 255, message = "Invalid string length")
    @NotBlank(message = "username is required")
    @Pattern(regexp = "^\\w+$")
    private String username;

    @Size(max = 255, message = "Invalid string length")
    @NotBlank(message = "email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 255, message = "Invalid string length")
    @NotBlank(message = "Password is required")
    private String password;

    @Size(max = 255, message = "Invalid string length")
    private String firstName;

    @Size(max = 255, message = "Invalid string length")
    private String lastName;

}


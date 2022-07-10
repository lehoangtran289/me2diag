package com.hust.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.UserGenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) // not work with modelattribute
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientRegisterRequestDTO {
    @Size(max = 255, message = "Invalid string length")
    private String id;
    @Size(max = 255, message = "Invalid string length")
    @NotBlank(message = "name is required")
    private String name;
    private String phoneNo;
    private String email;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private UserGenderEnum gender;
    private MultipartFile avatar;
}
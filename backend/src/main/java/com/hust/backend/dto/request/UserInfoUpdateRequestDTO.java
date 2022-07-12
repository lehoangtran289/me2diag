package com.hust.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.UserGenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) // not work
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoUpdateRequestDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private MultipartFile avatar;

    private String phoneNo;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private UserGenderEnum gender;
    private String description;

    public boolean isAllPropertiesNull() {
        return username == null && email == null && firstName == null && lastName == null && avatar == null
                && birthDate == null && gender == null && description == null;
    }
}


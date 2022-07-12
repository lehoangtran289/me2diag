package com.hust.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hust.backend.constant.UserGenderEnum;
import com.hust.backend.constant.UserRoleEnum;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private UserGenderEnum gender;
    @JsonProperty("isEnable")
    private boolean isEnable;
    private Date updatedAt;
    private List<UserRoleEnum> roles;
}


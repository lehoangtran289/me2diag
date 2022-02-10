package com.hust.backend.dto.response;

import com.hust.backend.constant.UserGenderEnum;
import lombok.*;

import java.util.Date;

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
    private String avatarUrl;
    private Date birthDate;
    private UserGenderEnum gender;
    private String description;
    private Date updatedAt;
}


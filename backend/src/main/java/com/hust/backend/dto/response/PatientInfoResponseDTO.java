package com.hust.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hust.backend.constant.UserGenderEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientInfoResponseDTO {
    private String id;
    private String name;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date birthDate;
    private String avatarUrl;
    private UserGenderEnum gender;
    private Date updatedAt;
}

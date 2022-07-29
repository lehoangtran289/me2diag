package com.hust.backend.dto.response;

import com.hust.backend.constant.ApplicationEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExaminationResponseDTO {
    private String id;
    private ApplicationEnum appId;
    private String patientId;
    private String patientName;
    private Date birthDate;
    private String userFullName;
    private String userEmail;
    private String result;
    private Date createdAt;
}

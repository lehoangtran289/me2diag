package com.hust.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hust.backend.constant.ApplicationEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class ExamResultResponseDTO {
    //
    private ApplicationEnum applicationId;
    private String examinationId;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date date;
    //
    private String patientId;
    private String patientName;
    private Date patientBirthDate;
    private String patientPhoneNo;
    private String patientAvatar;
    private String patientAddress;
    private String patientEmail;
    //
    private String userFullName;
    private String userEmail;
}

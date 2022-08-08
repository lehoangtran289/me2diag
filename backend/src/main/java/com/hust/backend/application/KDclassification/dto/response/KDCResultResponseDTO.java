package com.hust.backend.application.KDclassification.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hust.backend.application.KDclassification.constant.KDCResultEnum;
import com.hust.backend.constant.ApplicationEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KDCResultResponseDTO {
    //
    private ApplicationEnum applicationId;
    private String examinationId;
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
    private KDCResultDTO result;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date date;

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KDCResultDTO {
        private String wbc;
        private String ly;
        private String ne;
        private String rbc;
        private String hgb;
        private String hct;
        private String plt;
        private String na;
        private String k;
        private String totalProtein;
        private String albumin;
        private String ure;
        private String creatinin;
        private KDCResultEnum result;
    }
}


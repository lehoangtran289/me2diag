package com.hust.backend.application.KDclassification.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hust.backend.application.KDclassification.constant.KDCResultEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KDCResultResponseDTO {
    private String examinationId;
    private String patientId;
    private String patientName;
    private Date birthDate;
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
        private Double wbc;
        private Double ly;
        private Double ne;
        private Double rbc;
        private Double hgb;
        private Double hct;
        private Double plt;
        private Double na;
        private Double k;
        private Double totalProtein;
        private Double albumin;
        private Double ure;
        private Double creatinin;
        private KDCResultEnum result;
    }
}


package com.hust.backend.application.KDclassification.dto.response;

import com.hust.backend.application.KDclassification.constant.KDCResultEnum;
import com.hust.backend.dto.response.ExamResultResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class KDCResultResponseDTO extends ExamResultResponseDTO {
    private KDCResultDTO result;
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


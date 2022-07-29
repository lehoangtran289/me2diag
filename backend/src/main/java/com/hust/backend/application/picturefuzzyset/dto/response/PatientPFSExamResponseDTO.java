package com.hust.backend.application.picturefuzzyset.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientPFSExamResponseDTO {
    private String examinationId;
    private String patientId;
    private String patientName;
    private Date birthDate;
    private String userFullName;
    private String userEmail;
    private List<Map.Entry<SymptomEnum, PictureFuzzySet>> symptoms;
    private List<Map.Entry<DiagnoseEnum, Double>> result;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date date;
}

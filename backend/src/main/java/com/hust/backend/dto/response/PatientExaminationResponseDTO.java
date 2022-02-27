package com.hust.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.DiagnoseEnum;
import com.hust.backend.constant.SymptomEnum;
import com.hust.backend.model.PictureFuzzySet;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientExaminationResponseDTO {
    private String examinationId;
    private String username;
    private String patientId;
    private String patientName;
    private List<Map.Entry<SymptomEnum, PictureFuzzySet>> symptoms;
    private List<Map.Entry<DiagnoseEnum, Double>> result;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date date;
}

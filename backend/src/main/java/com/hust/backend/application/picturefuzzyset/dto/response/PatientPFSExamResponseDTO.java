package com.hust.backend.application.picturefuzzyset.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
import com.hust.backend.dto.response.ExamResultResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientPFSExamResponseDTO extends ExamResultResponseDTO {
    private List<Map.Entry<SymptomEnum, PictureFuzzySet>> symptoms;
    private List<Map.Entry<DiagnoseEnum, Double>> result;
}

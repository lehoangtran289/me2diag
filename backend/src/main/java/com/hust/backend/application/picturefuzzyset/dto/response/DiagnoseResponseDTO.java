package com.hust.backend.application.picturefuzzyset.dto.response;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiagnoseResponseDTO {
    private String examinationId;
    private String patientId;
    private List<Map.Entry<DiagnoseEnum, Double>> result;
}

package com.hust.backend.dto.response;

import com.hust.backend.constant.DiagnoseEnum;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiagnoseResponseDTO {
    private String patientId;
    private List<Map.Entry<DiagnoseEnum, Double>> result;
}

package com.hust.backend.application.KDclassification.dto.response;

import com.hust.backend.application.KDclassification.constant.KDCResultEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KDCDiagnoseResponseDTO {
    private String examinationId;
    private String patientId;
    private KDCResultEnum result;
}


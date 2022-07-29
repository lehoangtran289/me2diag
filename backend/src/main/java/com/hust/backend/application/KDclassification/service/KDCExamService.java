package com.hust.backend.application.KDclassification.service;

import com.hust.backend.application.KDclassification.dto.request.KDCRequestDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCDiagnoseResponseDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCResultResponseDTO;
import com.hust.backend.entity.ExaminationEntity;

public interface KDCExamService {
    KDCDiagnoseResponseDTO diagnose(String userId, KDCRequestDTO request);

    KDCResultResponseDTO buildExamResult(ExaminationEntity e);
}

package com.hust.backend.application.KDclassification.service;

import com.hust.backend.application.KDclassification.dto.request.KDCRequestDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCDiagnoseResponseDTO;

public interface KDCService {
    KDCDiagnoseResponseDTO diagnose(String userId, KDCRequestDTO request);
}

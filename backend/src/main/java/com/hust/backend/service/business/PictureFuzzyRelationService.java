package com.hust.backend.service.business;

import com.hust.backend.dto.response.DiagnoseResponseDTO;
import com.hust.backend.entity.PatientSymptomEntity;

import java.util.List;

public interface PictureFuzzyRelationService {
    DiagnoseResponseDTO diagnose(
            String examinationId, String userId, String patientId, List<PatientSymptomEntity> symptoms);
}

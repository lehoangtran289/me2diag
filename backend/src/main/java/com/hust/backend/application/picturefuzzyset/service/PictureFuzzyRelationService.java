package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.response.DiagnoseResponseDTO;
import com.hust.backend.application.picturefuzzyset.entity.PatientSymptomEntity;

import java.util.List;

public interface PictureFuzzyRelationService {
    DiagnoseResponseDTO diagnose(
            String examinationId, String userId, String patientId, List<PatientSymptomEntity> symptoms);
}

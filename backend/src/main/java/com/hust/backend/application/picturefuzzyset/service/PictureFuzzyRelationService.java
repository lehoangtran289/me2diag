package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.response.DiagnoseResponseDTO;
import com.hust.backend.application.picturefuzzyset.entity.PatientSymptomEntity;
import com.hust.backend.application.picturefuzzyset.model.GeneralPictureFuzzySet;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;

import java.util.List;

public interface PictureFuzzyRelationService {
    DiagnoseResponseDTO diagnose(
            String examinationId, String userId, String patientId, List<PatientSymptomEntity> symptoms);

    PictureFuzzySet convertGeneralPFSToPFS(GeneralPictureFuzzySet data);
}

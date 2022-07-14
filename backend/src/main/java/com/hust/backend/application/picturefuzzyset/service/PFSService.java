package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.request.GeneralDiagnoseRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.request.SymptomDiagnoseConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PFSDiagnoseResponseDTO;
import com.hust.backend.application.picturefuzzyset.model.SymptomDiagnoseConfig;

import java.util.List;

public interface PFSService {
    PFSDiagnoseResponseDTO diagnose(String userId, GeneralDiagnoseRequestDTO request);

    Boolean changePFSConfigs(List<SymptomDiagnoseConfigRequestDTO> request);

    List<SymptomDiagnoseConfig> getPFSConfigs();
}

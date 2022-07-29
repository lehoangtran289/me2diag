package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.request.SymptomDiagnoseConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.model.SymptomDiagnoseConfig;

import java.util.List;

public interface PFSConfigService {
    Boolean changePFSConfigs(List<SymptomDiagnoseConfigRequestDTO> request);

    List<SymptomDiagnoseConfig> getPFSConfigs();
}

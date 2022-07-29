package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.request.GeneralDiagnoseRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PFSDiagnoseResponseDTO;

public interface PFSService {
    PFSDiagnoseResponseDTO diagnose(String userId, GeneralDiagnoseRequestDTO request);
}

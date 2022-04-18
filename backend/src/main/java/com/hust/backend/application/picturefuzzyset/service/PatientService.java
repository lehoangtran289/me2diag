package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.request.PatientRegisterRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PatientInfoResponseDTO;
import com.hust.backend.factory.PagingInfo;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    PagingInfo<PatientInfoResponseDTO> getALlPatients(String query, Pageable pageable);

    PatientInfoResponseDTO getPatient(String patientId);

    void registerPatient(PatientRegisterRequestDTO request);
}

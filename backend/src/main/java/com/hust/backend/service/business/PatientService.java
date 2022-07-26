package com.hust.backend.service.business;

import com.hust.backend.dto.request.PatientEditRequestDTO;
import com.hust.backend.dto.request.PatientRegisterRequestDTO;
import com.hust.backend.dto.response.PatientInfoResponseDTO;
import com.hust.backend.factory.PagingInfo;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    PagingInfo<PatientInfoResponseDTO> getALlPatients(String query, String gender, Pageable pageable);

    PatientInfoResponseDTO getPatient(String patientId);

    void registerPatient(PatientRegisterRequestDTO request);

    void deletePatient(String patientId);

    void editPatient(String patientId, PatientEditRequestDTO request);
}

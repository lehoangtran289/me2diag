package com.hust.backend.service.business;

import com.hust.backend.dto.response.ExamIDListResponseDTO;
import com.hust.backend.dto.response.PatientExaminationResponseDTO;

import java.util.List;

public interface ExaminationService {
    PatientExaminationResponseDTO getExamination(String id);

    List<ExamIDListResponseDTO> getPatientExaminations(String patientId);
}

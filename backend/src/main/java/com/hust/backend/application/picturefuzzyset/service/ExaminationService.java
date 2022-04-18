package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.response.ExamIDListResponseDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PatientExaminationResponseDTO;
import com.hust.backend.factory.PagingInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExaminationService {
    PatientExaminationResponseDTO getExamination(String id);

    List<ExamIDListResponseDTO> getPatientExaminations(String patientId);

    PagingInfo<PatientExaminationResponseDTO> getAllExaminations(String patientID, Pageable pageable);
}

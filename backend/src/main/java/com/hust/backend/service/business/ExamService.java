package com.hust.backend.service.business;

import com.hust.backend.application.picturefuzzyset.dto.response.ExamInfoResponseDTO;

import java.util.List;

public interface ExamService {
    List<ExamInfoResponseDTO> getPatientExaminations(String patientId);
}

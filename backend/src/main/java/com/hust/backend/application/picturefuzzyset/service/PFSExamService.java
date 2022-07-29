package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.response.ExamInfoResponseDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PatientPFSExamResponseDTO;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.factory.PagingInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PFSExamService {
    PatientPFSExamResponseDTO getExamination(String id);

    PatientPFSExamResponseDTO buildExamResult(ExaminationEntity e);

    List<ExamInfoResponseDTO> getPatientExaminations(String patientId);

    PagingInfo<PatientPFSExamResponseDTO> getAllExaminations(String patientID, Pageable pageable);
}

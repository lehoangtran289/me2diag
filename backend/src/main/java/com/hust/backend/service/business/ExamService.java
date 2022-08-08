package com.hust.backend.service.business;

import com.hust.backend.application.picturefuzzyset.dto.response.ExamInfoResponseDTO;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.dto.response.ExamResultResponseDTO;
import com.hust.backend.dto.response.ExaminationResponseDTO;
import com.hust.backend.factory.PagingInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamService {
    List<ExamInfoResponseDTO> getPatientExaminations(String patientId);

    ExamResultResponseDTO getExamination(String examinationId);

    PagingInfo<ExaminationResponseDTO> getAllExaminations(ApplicationEnum appId, String query, Pageable pageable);
}

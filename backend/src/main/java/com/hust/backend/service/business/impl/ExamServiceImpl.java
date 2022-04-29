package com.hust.backend.service.business.impl;

import com.hust.backend.application.picturefuzzyset.dto.response.ExamInfoResponseDTO;
import com.hust.backend.repository.ExamRepository;
import com.hust.backend.service.business.ExamService;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public List<ExamInfoResponseDTO> getPatientExaminations(String patientId) {
        return Transformer.listToList(
                examRepository.findAllByPatientId(patientId),
                e -> ExamInfoResponseDTO.builder()
                        .examinationId(e.getId())
                        .applicationId(e.getAppId())
                        .date(e.getCreatedAt())
                        .build()
        );
    }
}

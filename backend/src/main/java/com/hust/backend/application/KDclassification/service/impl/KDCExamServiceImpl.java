package com.hust.backend.application.KDclassification.service.impl;

import com.hust.backend.application.KDclassification.dto.response.KDCResultResponseDTO;
import com.hust.backend.application.KDclassification.entity.KDCExamResultEntity;
import com.hust.backend.application.KDclassification.repository.KDCExamResultRepository;
import com.hust.backend.application.KDclassification.service.KDCExamService;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.repository.PatientRepository;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KDCExamServiceImpl implements KDCExamService {
    private final KDCExamResultRepository examResultRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public KDCExamServiceImpl(KDCExamResultRepository examResultRepository,
                              PatientRepository patientRepository,
                              UserRepository userRepository) {
        this.examResultRepository = examResultRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public KDCResultResponseDTO buildExamResult(ExaminationEntity e) {
        PatientEntity patientEntity = patientRepository.findById(e.getPatientId())
                .orElseThrow(() -> new NotFoundException(PatientEntity.class, e.getPatientId()));
        UserEntity user = userRepository.findById(e.getUserId())
                .orElseThrow(() -> new NotFoundException(UserEntity.class, e.getUserId()));
        KDCExamResultEntity result = examResultRepository.findById(e.getId())
                .orElseThrow(() -> new NotFoundException(KDCExamResultEntity.class, e.getId()));
        return KDCResultResponseDTO.builder()
                .applicationId(e.getAppId())
                .examinationId(e.getId())
                .result(Common.convertObject(result, KDCResultResponseDTO.KDCResultDTO.class))
                .date(e.getCreatedAt())
                //
                .patientName(patientEntity.getName())
                .patientId(patientEntity.getId())
                .patientBirthDate(patientEntity.getBirthDate())
                .patientEmail(patientEntity.getEmail())
                .patientAddress(patientEntity.getAddress())
                .patientPhoneNo(patientEntity.getPhoneNo())
                //
                .userFullName(user.getFirstName() + " " + user.getLastName())
                .userEmail(user.getEmail())
                .build();
    }
}

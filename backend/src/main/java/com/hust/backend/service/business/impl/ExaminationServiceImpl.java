package com.hust.backend.service.business.impl;

import com.hust.backend.config.AppConfig;
import com.hust.backend.constant.DiagnoseEnum;
import com.hust.backend.constant.SymptomEnum;
import com.hust.backend.dto.response.ExamIDListResponseDTO;
import com.hust.backend.dto.response.PatientExaminationResponseDTO;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.ExaminationResultEntity;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.model.PictureFuzzySet;
import com.hust.backend.repository.*;
import com.hust.backend.service.business.ExaminationService;
import com.hust.backend.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExaminationServiceImpl implements ExaminationService {
    private final PatientRepository patientRepository;
    private final ExaminationRepository examinationRepository;
    private final ExaminationResultRepository examinationResultRepository;
    private final PatientSymptomRepository patientSymptomRepository;
    private final UserRepository userRepository;
    private final SymptomDiagnoseRepository symptomDiagnoseRepository;

    public ExaminationServiceImpl(PatientRepository patientRepository,
                                  ExaminationRepository examinationRepository,
                                  ExaminationResultRepository examinationResultRepository,
                                  PatientSymptomRepository patientSymptomRepository,
                                  UserRepository userRepository,
                                  SymptomDiagnoseRepository symptomDiagnoseRepository) {
        this.patientRepository = patientRepository;
        this.examinationRepository = examinationRepository;
        this.examinationResultRepository = examinationResultRepository;
        this.patientSymptomRepository = patientSymptomRepository;
        this.userRepository = userRepository;
        this.symptomDiagnoseRepository = symptomDiagnoseRepository;
    }

    @Override
    public PatientExaminationResponseDTO getExamination(String id) {
        ExaminationEntity examinationEntity = examinationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExaminationEntity.class, id));
        UserEntity userEntity = userRepository.findById(examinationEntity.getUserId())
                .orElseThrow(() -> new NotFoundException(UserEntity.class, examinationEntity.getUserId()));
        PatientEntity patientEntity = patientRepository.findById(examinationEntity.getPatientId())
                .orElseThrow(() -> new NotFoundException(PatientEntity.class, examinationEntity.getPatientId()));

        List<Map.Entry<DiagnoseEnum, Double>> result = examinationResultRepository.findAllByExaminationId(id)
                .stream()
                .map(entity -> new AbstractMap.SimpleEntry<>(entity.getDiagnose(), entity.getProbability()))
                .collect(Collectors.toList());
        List<Map.Entry<SymptomEnum, PictureFuzzySet>> symptoms = patientSymptomRepository.findAllByExaminationId(id)
                .stream()
                .map(entity -> new AbstractMap.SimpleEntry<>(entity.getSymptom(), entity.getPictureFuzzySet()))
                .collect(Collectors.toList());

        return PatientExaminationResponseDTO.builder()
                .examinationId(id)
                .username(userEntity.getFirstName() + " " + userEntity.getLastName())
                .patientName(patientEntity.getName())
                .symptoms(symptoms)
                .result(result)
                .date(examinationEntity.getCreatedAt())
                .build();
    }

    @Override
    public List<ExamIDListResponseDTO> getPatientExaminations(String patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException(PatientEntity.class, patientId));
        return examinationRepository.findAllByPatientId(patientId)
                .stream()
                .map(e -> new ExamIDListResponseDTO(e.getId(), e.getCreatedAt()))
                .collect(Collectors.toList());
    }
}

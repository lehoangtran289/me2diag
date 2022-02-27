package com.hust.backend.service.business.impl;

import com.hust.backend.config.AppConfig;
import com.hust.backend.constant.DiagnoseEnum;
import com.hust.backend.constant.SymptomEnum;
import com.hust.backend.dto.response.ExamIDListResponseDTO;
import com.hust.backend.dto.response.PatientExaminationResponseDTO;
import com.hust.backend.dto.response.PatientInfoResponseDTO;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.ExaminationResultEntity;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.model.PictureFuzzySet;
import com.hust.backend.repository.*;
import com.hust.backend.service.business.ExaminationService;
import com.hust.backend.service.storage.StorageService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
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
    public PagingInfo<PatientExaminationResponseDTO> getAllExaminations(String patientID, Pageable pageable) {
        Page<ExaminationEntity> examinationEntityPage = StringUtils.isBlank(patientID) ?
                examinationRepository.findAllByOrderByCreatedAtDesc(pageable) :
                examinationRepository.findAllByPatientId(patientID, pageable);
        List<ExaminationEntity> examinationEntities = examinationEntityPage.getContent();

        List<PatientExaminationResponseDTO> results = new ArrayList<>();

        for (ExaminationEntity e : examinationEntities) {
            UserEntity userEntity = userRepository.findById(e.getUserId())
                    .orElseThrow(() -> new NotFoundException(UserEntity.class, e.getUserId()));

            PatientEntity patientEntity = patientRepository.findById(e.getPatientId())
                    .orElseThrow(() -> new NotFoundException(PatientEntity.class, e.getPatientId()));

            List<Map.Entry<DiagnoseEnum, Double>> result = examinationResultRepository.findAllByExaminationId(e.getId())
                    .stream()
                    .map(entity -> new AbstractMap.SimpleEntry<>(entity.getDiagnose(), entity.getProbability()))
                    .collect(Collectors.toList());

            List<Map.Entry<SymptomEnum, PictureFuzzySet>> symptoms = patientSymptomRepository.findAllByExaminationId(e.getId())
                    .stream()
                    .map(entity -> new AbstractMap.SimpleEntry<>(entity.getSymptom(), entity.getPictureFuzzySet()))
                    .collect(Collectors.toList());

            results.add(PatientExaminationResponseDTO.builder()
                    .examinationId(e.getId())
                    .username(userEntity.getFirstName() + " " + userEntity.getLastName())
                    .patientName(patientEntity.getName())
                    .patientId(patientEntity.getId())
                    .symptoms(symptoms)
                    .result(result)
                    .date(e.getCreatedAt())
                    .build());
        }
        return PagingInfo.<PatientExaminationResponseDTO>builder()
                .items(results)
                .currentPage(examinationEntityPage.getNumber())
                .totalItems(examinationEntityPage.getTotalElements())
                .totalPages(examinationEntityPage.getTotalPages())
                .build();
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
                .patientId(patientEntity.getId())
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

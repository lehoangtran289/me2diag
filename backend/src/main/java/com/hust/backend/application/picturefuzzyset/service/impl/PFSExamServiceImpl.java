package com.hust.backend.application.picturefuzzyset.service.impl;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.application.picturefuzzyset.dto.response.ExamInfoResponseDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PatientPFSExamResponseDTO;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
import com.hust.backend.application.picturefuzzyset.repository.PFSExamResultRepository;
import com.hust.backend.application.picturefuzzyset.repository.PatientSymptomRepository;
import com.hust.backend.application.picturefuzzyset.service.PFSExamService;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.repository.ApplicationRepository;
import com.hust.backend.repository.ExamRepository;
import com.hust.backend.repository.PatientRepository;
import com.hust.backend.repository.UserRepository;
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
public class PFSExamServiceImpl implements PFSExamService {
    private final PatientRepository patientRepository;
    private final ExamRepository examRepository;
    private final ApplicationRepository applicationRepository;
    private final PFSExamResultRepository pfsExamResultRepository;
    private final PatientSymptomRepository patientSymptomRepository;
    private final UserRepository userRepository;

    public PFSExamServiceImpl(PatientRepository patientRepository,
                              ExamRepository ExamRepository,
                              ApplicationRepository applicationRepository,
                              PFSExamResultRepository pfsExamResultRepository,
                              PatientSymptomRepository patientSymptomRepository,
                              UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.examRepository = ExamRepository;
        this.applicationRepository = applicationRepository;
        this.pfsExamResultRepository = pfsExamResultRepository;
        this.patientSymptomRepository = patientSymptomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PagingInfo<PatientPFSExamResponseDTO> getAllExaminations(String patientID, Pageable pageable) {
        Page<ExaminationEntity> examinationEntityPage = StringUtils.isBlank(patientID) ?
                examRepository.findAllByAppIdOrderByCreatedAtDesc(ApplicationEnum.PFS, pageable) :
                examRepository.findAllByAppIdAndPatientId(ApplicationEnum.PFS, patientID, pageable);
        List<ExaminationEntity> examinationEntities = examinationEntityPage.getContent();

        List<PatientPFSExamResponseDTO> results = new ArrayList<>();

        for (ExaminationEntity e : examinationEntities) {
            results.add(buildExamResult(e));
        }
        return PagingInfo.<PatientPFSExamResponseDTO>builder()
                .items(results)
                .currentPage(examinationEntityPage.getNumber())
                .totalItems(examinationEntityPage.getTotalElements())
                .totalPages(examinationEntityPage.getTotalPages())
                .build();
    }

    @Override
    public PatientPFSExamResponseDTO getExamination(String id) {
        ExaminationEntity examinationEntity = examRepository.findByIdAndAppId(id, ApplicationEnum.PFS)
                .orElseThrow(() -> new NotFoundException(ExaminationEntity.class, id));
        return buildExamResult(examinationEntity);
    }

    @Override
    public List<ExamInfoResponseDTO> getPatientExaminations(String patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException(PatientEntity.class, patientId));
        return examRepository.findAllByAppIdAndPatientId(ApplicationEnum.PFS, patientId)
                .stream()
                .map(e -> new ExamInfoResponseDTO(e.getId(), ApplicationEnum.PFS, e.getCreatedAt()))
                .collect(Collectors.toList());
    }

    private PatientPFSExamResponseDTO buildExamResult(ExaminationEntity e) {
        UserEntity userEntity = userRepository.findById(e.getUserId())
                .orElseThrow(() -> new NotFoundException(UserEntity.class, e.getUserId()));

        PatientEntity patientEntity = patientRepository.findById(e.getPatientId())
                .orElseThrow(() -> new NotFoundException(PatientEntity.class, e.getPatientId()));

        List<Map.Entry<DiagnoseEnum, Double>> result = pfsExamResultRepository.findAllByExaminationId(e.getId())
                .stream()
                .map(entity -> new AbstractMap.SimpleEntry<>(entity.getDiagnose(), entity.getProbability()))
                .collect(Collectors.toList());

        List<Map.Entry<SymptomEnum, PictureFuzzySet>> symptoms = patientSymptomRepository.findAllByExaminationId(e.getId())
                .stream()
                .map(entity -> new AbstractMap.SimpleEntry<>(entity.getSymptom(), entity.getPictureFuzzySet()))
                .collect(Collectors.toList());

        return PatientPFSExamResponseDTO.builder()
                .examinationId(e.getId())
                .username(userEntity.getFirstName() + " " + userEntity.getLastName())
                .patientName(patientEntity.getName())
                .patientId(patientEntity.getId())
                .symptoms(symptoms)
                .result(result)
                .date(e.getCreatedAt())
                .build();
    }

}

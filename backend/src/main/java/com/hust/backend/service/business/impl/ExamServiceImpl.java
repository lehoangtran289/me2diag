package com.hust.backend.service.business.impl;

import com.hust.backend.application.KDclassification.entity.KDCExamResultEntity;
import com.hust.backend.application.KDclassification.repository.KDCExamResultRepository;
import com.hust.backend.application.KDclassification.service.KDCExamService;
import com.hust.backend.application.picturefuzzyset.dto.response.ExamInfoResponseDTO;
import com.hust.backend.application.picturefuzzyset.entity.PFSExamResultEntity;
import com.hust.backend.application.picturefuzzyset.repository.PFSExamResultRepository;
import com.hust.backend.application.picturefuzzyset.service.PFSExamService;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.dto.response.ExaminationResponseDTO;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.repository.ExamRepository;
import com.hust.backend.repository.PatientRepository;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.service.business.ExamService;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final KDCExamResultRepository kdcExamResultRepo;
    private final PFSExamResultRepository pfsExamResultRepo;
    private final PFSExamService pfsExamService;
    private final KDCExamService kdcExamService;

    public ExamServiceImpl(ExamRepository examRepository,
                           PatientRepository patientRepository,
                           UserRepository userRepository,
                           KDCExamResultRepository kdcExamResultRepo,
                           PFSExamResultRepository pfsExamResultRepo,
                           PFSExamService pfsExamService,
                           KDCExamService kdcExamService) {
        this.examRepository = examRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.kdcExamResultRepo = kdcExamResultRepo;
        this.pfsExamResultRepo = pfsExamResultRepo;
        this.pfsExamService = pfsExamService;
        this.kdcExamService = kdcExamService;
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

    @Override
    public Object getExamination(String examinationId) {
        ExaminationEntity examinationEntity = examRepository.findById(examinationId)
                .orElseThrow(() -> new NotFoundException(ExaminationEntity.class, examinationId));
        if (examinationEntity.getAppId() == ApplicationEnum.PFS)
            return pfsExamService.buildExamResult(examinationEntity);
        else if (examinationEntity.getAppId() == ApplicationEnum.KDC)
            return kdcExamService.buildExamResult(examinationEntity);
        return null;
    }

    // fixme: change to @Query to avoid redundant query tx
    @Override
    public PagingInfo<ExaminationResponseDTO> getAllExaminations(String query, Pageable pageable) {
        Page<ExaminationEntity> examinationEntityPage = examRepository.findAll(pageable);
        List<ExaminationEntity> examinationEntities = examinationEntityPage.getContent();

        List<ExaminationResponseDTO> results = new ArrayList<>();

        for (ExaminationEntity e : examinationEntities) {
            String result = null;
            if (e.getAppId() == ApplicationEnum.PFS) {
                List<PFSExamResultEntity> pfsResults = pfsExamResultRepo.findAllByExaminationId(e.getId());
                List<String> affectedDiseases = pfsResults.stream()
                        .filter(r -> r.getProbability() >= 0.5)
                        .map(r -> r.getDiagnose().name())
                        .collect(Collectors.toList());
                result = affectedDiseases.size() != 0 ?
                        StringUtils.join(affectedDiseases, ", ") :
                        "None";
            } else if (e.getAppId() == ApplicationEnum.KDC) {
                KDCExamResultEntity kdcExamResultEntity = kdcExamResultRepo.findById(e.getId())
                        .orElseThrow(() -> new NotFoundException(KDCExamResultEntity.class, e.getId()));
                result = kdcExamResultEntity.getResult().name();
            }
            UserEntity userEntity = userRepository.findById(e.getUserId())
                    .orElseThrow(() -> new NotFoundException(UserEntity.class, e.getUserId()));
            PatientEntity patientEntity = patientRepository.findById(e.getPatientId())
                    .orElseThrow(() -> new NotFoundException(PatientEntity.class, e.getPatientId()));
            results.add(ExaminationResponseDTO.builder()
                    .appId(e.getAppId())
                    .id(e.getId())
                    .patientId(patientEntity.getId())
                    .patientName(patientEntity.getName())
                    .birthDate(patientEntity.getBirthDate())
                    .userEmail(userEntity.getEmail())
                    .userFullName(userEntity.getFirstName() + " " + userEntity.getLastName())
                    .result(result)
                    .createdAt(e.getCreatedAt())
                    .build());
        }
        return PagingInfo.<ExaminationResponseDTO>builder()
                .items(results)
                .currentPage(examinationEntityPage.getNumber())
                .totalItems(examinationEntityPage.getTotalElements())
                .totalPages(examinationEntityPage.getTotalPages())
                .build();
    }
}

package com.hust.backend.application.picturefuzzyset.service.impl;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.application.picturefuzzyset.dto.request.GeneralDiagnoseRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.request.SymptomDiagnoseConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.PFSDiagnoseResponseDTO;
import com.hust.backend.application.picturefuzzyset.entity.PFSExamResultEntity;
import com.hust.backend.application.picturefuzzyset.entity.PatientSymptomEntity;
import com.hust.backend.application.picturefuzzyset.entity.SymptomDiagnoseEntity;
import com.hust.backend.application.picturefuzzyset.model.GeneralPictureFuzzySet;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
import com.hust.backend.application.picturefuzzyset.model.SymptomDiagnoseConfig;
import com.hust.backend.application.picturefuzzyset.repository.PFSExamResultRepository;
import com.hust.backend.application.picturefuzzyset.repository.PatientSymptomRepository;
import com.hust.backend.application.picturefuzzyset.repository.SymptomDiagnoseRepository;
import com.hust.backend.application.picturefuzzyset.service.PFSService;
import com.hust.backend.application.picturefuzzyset.utils.PFSCommon;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.exception.InternalException;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.repository.ExamRepository;
import com.hust.backend.repository.LinguisticDomainRepository;
import com.hust.backend.repository.PatientRepository;
import com.hust.backend.service.business.HedgeAlgebraService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import com.hust.backend.utils.ULID;
import com.hust.backend.utils.tuple.Tuple3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class PFSServiceImpl implements PFSService {
    private final PatientRepository patientRepo;
    private final ExamRepository examRepo;
    private final PFSExamResultRepository pfsExamResultRepo;
    private final PatientSymptomRepository patientSymptomRepo;
    private final SymptomDiagnoseRepository symptomDiagnoseRepo;
    private final LinguisticDomainRepository linguisticDomRepo;
    private final HedgeAlgebraService HAService;

    public PFSServiceImpl(PatientRepository patientRepo,
                          ExamRepository examRepo,
                          PFSExamResultRepository pfsExamResultRepo,
                          PatientSymptomRepository patientSymptomRepo,
                          SymptomDiagnoseRepository symptomDiagnoseRepo,
                          LinguisticDomainRepository linguisticDomRepo,
                          HedgeAlgebraService haService) {
        this.patientRepo = patientRepo;
        this.examRepo = examRepo;
        this.pfsExamResultRepo = pfsExamResultRepo;
        this.patientSymptomRepo = patientSymptomRepo;
        this.symptomDiagnoseRepo = symptomDiagnoseRepo;
        this.linguisticDomRepo = linguisticDomRepo;
        HAService = haService;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean changePFSConfigs(List<SymptomDiagnoseConfigRequestDTO> request) {
        List<SymptomDiagnoseEntity> updatedPFSConfigs = new ArrayList<>();
        for (SymptomDiagnoseConfigRequestDTO config: request) {
            if (!PFSCommon.isValidPFS(config.getPictureFuzzySet()))
                throw new InternalException("Invalid picture fuzzy set");

            updatedPFSConfigs.add(Common.convertObject(config, SymptomDiagnoseEntity.class));
        }
        symptomDiagnoseRepo.saveAll(updatedPFSConfigs);
        return true;
    }

    @Override
    public List<SymptomDiagnoseConfig> getPFSConfigs() {
        return Transformer.listToList(
                symptomDiagnoseRepo.findAll(),
                sdEntity -> Common.convertObject(sdEntity, SymptomDiagnoseConfig.class));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PFSDiagnoseResponseDTO diagnose(String userId, GeneralDiagnoseRequestDTO request) {
        // build input
        String patientId = request.getPatientId();
        if (!patientRepo.existsById(patientId)) {
            log.error("patientId not found, id = {}", patientId);
            throw new NotFoundException(PatientEntity.class, patientId);
        }

        // save exam
        String examinationId = ULID.nextULID();
        examRepo.save(ExaminationEntity.builder()
                .id(examinationId)
                .appId(ApplicationEnum.PFS)
                .userId(userId)
                .patientId(patientId)
                .build());

        // build patient symptoms relations
        List<PatientSymptomEntity> patientSymptoms = new ArrayList<>();
        for (Map.Entry<String, GeneralPictureFuzzySet> data : request.getSymptoms()) {
            PictureFuzzySet pfs = convertGeneralPFSToPFS(data.getValue());
            if (!PFSCommon.isValidPFS(pfs)) {
                log.error("PFS not valid, {}", pfs);
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "PFS not valid");
            }
            patientSymptoms.add(PatientSymptomEntity.builder()
                    .examinationId(examinationId)
                    .symptom(SymptomEnum.from(data.getKey()))
                    .pictureFuzzySet(pfs)
                    .build());
        }
        // save patient symptoms
        patientSymptomRepo.saveAll(patientSymptoms);

        return diagnose(examinationId, patientId, patientSymptoms);
    }

    private PFSDiagnoseResponseDTO diagnose(
            String examinationId,
            String patientId,
            List<PatientSymptomEntity> PSRelations // 1x5 matrix patient_symptom
    ) {
        log.info("PFS - Diagnose patient {} with data: {}", patientId, PSRelations);

        List<PFSExamResultEntity> examResults = new ArrayList<>();
        for (DiagnoseEnum diagnoseEnum : DiagnoseEnum.values()) {
            // get Symptom_Diagnose relations -> 5x1 matrix symptom_diagnose
            List<SymptomDiagnoseEntity> SDRelations = symptomDiagnoseRepo.getAllByDiagnose(diagnoseEnum);

            // sort by `SymptomEnum` to match patient_symptom relations
            SDRelations.sort(Comparator.comparing(SymptomDiagnoseEntity::getSymptom));

            // validate matrix sizes for Patient_Diagnose composition
            if (SDRelations.size() != PSRelations.size()) {
                log.error("Fuzzy relation size error, size PS = {}, size SD = {}", PSRelations.size(),
                        SDRelations.size());
                throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "fuzzy relation size " +
                        "error");
            }

            // process fuzzy relation composition
            Tuple3<DiagnoseEnum, PictureFuzzySet, Double> diagnoseResult =
                    PFSCommon.computeRelation(diagnoseEnum, PSRelations, SDRelations);

            // save to examinationResult
            examResults.add(PFSExamResultEntity.builder()
                    .examinationId(examinationId)
                    .diagnose(diagnoseResult.getA0())
                    .probability(diagnoseResult.getA2())
                    .build());

            log.info("Patient {} diagnosis: {} - {} - {}",
                    patientId, diagnoseEnum, diagnoseResult.getA1(), diagnoseResult.getA2());
        }

        // save exam results
        pfsExamResultRepo.saveAll(examResults);

        // response result
        return PFSDiagnoseResponseDTO.builder()
                .examinationId(examinationId)
                .patientId(patientId)
                .result(Transformer.listToList(
                        examResults,
                        res -> new AbstractMap.SimpleEntry<>(res.getDiagnose(), res.getProbability())
                ))
                .build();
    }

    private PictureFuzzySet convertGeneralPFSToPFS(GeneralPictureFuzzySet gpfs) {
        // if not linguistic domain -> throw
        if (!PFSCommon.isValidGPFS(gpfs))
            throw new InternalException("Picture Fuzzy Set type must be enum STRING or DOUBLE");

        return PictureFuzzySet.builder()
                .positive(gpfs.getPositive() instanceof Double ? (Double) gpfs.getPositive() :
                        HAService.getVValueFromLinguistic(ApplicationEnum.PFS, (String) gpfs.getPositive()))
                .neutral(gpfs.getNeutral() instanceof Double ? (Double) gpfs.getNeutral() :
                        HAService.getVValueFromLinguistic(ApplicationEnum.PFS, (String) gpfs.getNeutral()))
                .negative(gpfs.getNegative() instanceof Double ? (Double) gpfs.getNegative() :
                        HAService.getVValueFromLinguistic(ApplicationEnum.PFS, (String) gpfs.getNegative()))
                .build();
    }
}

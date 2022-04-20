package com.hust.backend.application.picturefuzzyset.service.impl;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.constant.LinguisticDomainEnum;
import com.hust.backend.application.picturefuzzyset.dto.response.DiagnoseResponseDTO;
import com.hust.backend.application.picturefuzzyset.entity.*;
import com.hust.backend.application.picturefuzzyset.model.GeneralPictureFuzzySet;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
import com.hust.backend.application.picturefuzzyset.repository.*;
import com.hust.backend.application.picturefuzzyset.service.PictureFuzzyRelationService;
import com.hust.backend.application.picturefuzzyset.utils.PFSCommon;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.exception.InternalException;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.utils.Transformer;
import com.hust.backend.utils.tuple.Tuple3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class PictureFuzzyRelationServiceImpl implements PictureFuzzyRelationService {
    private final PatientRepository patientRepository;
    private final ExaminationRepository examinationRepository;
    private final ExaminationResultRepository examinationResultRepository;
    private final PatientSymptomRepository patientSymptomRepository;
    private final SymptomDiagnoseRepository symptomDiagnoseRepository;
    private final HedgeAlgebraConfigRepository hedgeAlgebraConfigRepo;
    private final LinguisticDomainRepository linguisticDomainRepo;

    public PictureFuzzyRelationServiceImpl(PatientRepository patientRepository,
                                           ExaminationRepository examinationRepository,
                                           ExaminationResultRepository examinationResultRepository,
                                           PatientSymptomRepository patientSymptomRepository,
                                           SymptomDiagnoseRepository symptomDiagnoseRepository,
                                           HedgeAlgebraConfigRepository hedgeAlgebraConfigRepo,
                                           LinguisticDomainRepository linguisticDomainRepo) {
        this.patientRepository = patientRepository;
        this.examinationRepository = examinationRepository;
        this.examinationResultRepository = examinationResultRepository;
        this.patientSymptomRepository = patientSymptomRepository;
        this.symptomDiagnoseRepository = symptomDiagnoseRepository;
        this.hedgeAlgebraConfigRepo = hedgeAlgebraConfigRepo;
        this.linguisticDomainRepo = linguisticDomainRepo;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public DiagnoseResponseDTO diagnose(
            String examinationId,
            String userId,
            String patientId,
            List<PatientSymptomEntity> PSRelations // 1x5 matrix patient_symptom
    ) {
        if (!patientRepository.existsById(patientId)) {
            log.error("patientId not found, id = {}", patientId);
            throw new NotFoundException(PatientEntity.class, patientId);
        }

        log.info("diagnose patient {} with data: {}", patientId, PSRelations);

        // save new examination
        examinationRepository.save(ExaminationEntity.builder()
                .id(examinationId)
                .userId(userId)
                .patientId(patientId)
                .build());

        // save patient symptoms
        patientSymptomRepository.saveAll(PSRelations);

        List<ExaminationResultEntity> examResults = new ArrayList<>();
        for (DiagnoseEnum diagnoseEnum : DiagnoseEnum.values()) {
            // get Symptom_Diagnose relations -> 5x1 matrix symptom_diagnose
            List<SymptomDiagnoseEntity> SDRelations = symptomDiagnoseRepository.getAllByDiagnose(diagnoseEnum);

            // sort by SymptomEnum to match patient_symptom relations
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
            examResults.add(ExaminationResultEntity.builder()
                    .examinationId(examinationId)
                    .diagnose(diagnoseResult.getA0())
                    .probability(diagnoseResult.getA2())
                    .build());

            log.info("Patient {} diagnosis: {} - {} - {}",
                    patientId, diagnoseEnum, diagnoseResult.getA1(), diagnoseResult.getA2());
        }

        // save exam results
        examinationResultRepository.saveAll(examResults);

        // return result
        List<Map.Entry<DiagnoseEnum, Double>> result = Transformer.listToList(
                examResults,
                res -> new AbstractMap.SimpleEntry<>(res.getDiagnose(), res.getProbability())
        );
        return DiagnoseResponseDTO.builder()
                .examinationId(examinationId)
                .patientId(patientId)
                .result(result)
                .build();
    }

    // TODO: validate invalid linguistic input
    @Override
    public PictureFuzzySet convertGeneralPFSToPFS(GeneralPictureFuzzySet gpfs) {
        PictureFuzzySet pfs = new PictureFuzzySet();

        // if not linguistic domain -> throw
        if (!((gpfs.getPositive() instanceof Double || gpfs.getPositive() instanceof String) &&
                (gpfs.getNeutral() instanceof Double || gpfs.getNeutral() instanceof String) &&
                (gpfs.getNegative() instanceof Double || gpfs.getNegative() instanceof String))
        ) {
            throw new InternalException("Picture Fuzzy Set type must be enum STRING or DOUBLE");
        }

        // traverse fields, check type and set fields corresponding to type
        // pfs.positive
        if (gpfs.getPositive() instanceof Double) {
            pfs.setPositive((Double) gpfs.getPositive());
        } else {
            LinguisticDomainEnum linguisticDomainEnum = LinguisticDomainEnum.from((String) gpfs.getPositive());
            LinguisticDomainEntity linguisticEntity =
                    linguisticDomainRepo.findById(linguisticDomainEnum)
                            .orElseThrow(() -> new NotFoundException(LinguisticDomainEntity.class, (String) gpfs.getPositive()));
            pfs.setPositive(linguisticEntity.getVValue());
        }

        // pfs.neutral
        if (gpfs.getNeutral() instanceof Double) {
            pfs.setNeutral((Double) gpfs.getNeutral());
        } else {
            LinguisticDomainEnum linguisticDomainEnum = LinguisticDomainEnum.from((String) gpfs.getNeutral());
            LinguisticDomainEntity linguisticEntity =
                    linguisticDomainRepo.findById(linguisticDomainEnum)
                            .orElseThrow(() -> new NotFoundException(LinguisticDomainEntity.class, (String) gpfs.getNeutral()));
            pfs.setNeutral(linguisticEntity.getVValue());
        }

        // pfs.negative
        if (gpfs.getNegative() instanceof Double) {
            pfs.setNegative((Double) gpfs.getNegative());
        } else {
            LinguisticDomainEnum linguisticDomainEnum = LinguisticDomainEnum.from((String) gpfs.getNegative());
            LinguisticDomainEntity linguisticEntity =
                    linguisticDomainRepo.findById(linguisticDomainEnum)
                            .orElseThrow(() -> new NotFoundException(LinguisticDomainEntity.class, (String) gpfs.getNegative()));
            pfs.setNegative(linguisticEntity.getVValue());
        }
        return pfs;
    }
}

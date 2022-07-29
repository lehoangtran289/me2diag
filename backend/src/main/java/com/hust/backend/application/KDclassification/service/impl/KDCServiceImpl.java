package com.hust.backend.application.KDclassification.service.impl;

import com.hust.backend.application.KDclassification.constant.KDCDomainEnum;
import com.hust.backend.application.KDclassification.constant.KDCResultEnum;
import com.hust.backend.application.KDclassification.dto.request.KDCModelInputRequestDTO;
import com.hust.backend.application.KDclassification.dto.request.KDCRequestDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCDiagnoseResponseDTO;
import com.hust.backend.application.KDclassification.entity.KDCDomainEntity;
import com.hust.backend.application.KDclassification.entity.KDCExamResultEntity;
import com.hust.backend.application.KDclassification.repository.KDCDomainRepository;
import com.hust.backend.application.KDclassification.repository.KDCExamResultRepository;
import com.hust.backend.application.KDclassification.service.KDCService;
import com.hust.backend.application.KDclassification.service.partner.KDCModelService;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.LinguisticDomainEnum;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.LinguisticDomainEntity;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.entity.key.LinguisticApplicationEntityKey;
import com.hust.backend.exception.InternalException;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.repository.ExamRepository;
import com.hust.backend.repository.LinguisticDomainRepository;
import com.hust.backend.repository.PatientRepository;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.service.business.HedgeAlgebraService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.ULID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Slf4j
public class KDCServiceImpl implements KDCService {
    private final KDCDomainRepository kdcDomainRepository;
    private final KDCExamResultRepository examResultRepository;
    private final ExamRepository examRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final LinguisticDomainRepository linguisticDomRepo;
    private final HedgeAlgebraService HAService;
    private final KDCModelService kdcModelService;

    public KDCServiceImpl(KDCDomainRepository kdcDomainRepository,
                              KDCExamResultRepository examResultRepository,
                              ExamRepository examRepository,
                              PatientRepository patientRepository,
                              UserRepository userRepository,
                              LinguisticDomainRepository linguisticDomRepo,
                              HedgeAlgebraService HAService,
                              KDCModelService kdcModelService) {
        this.kdcDomainRepository = kdcDomainRepository;
        this.examResultRepository = examResultRepository;
        this.examRepository = examRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.linguisticDomRepo = linguisticDomRepo;
        this.HAService = HAService;
        this.kdcModelService = kdcModelService;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public KDCDiagnoseResponseDTO diagnose(String userId, KDCRequestDTO request) {
        // build input
        String patientId = request.getPatientId();
        if (!patientRepository.existsById(patientId)) {
            log.error("patientId not found, id = {}", patientId);
            throw new NotFoundException(PatientEntity.class, patientId);
        }

        // save new examination
        String examinationId = ULID.nextULID();
        examRepository.save(ExaminationEntity.builder()
                .id(examinationId)
                .appId(ApplicationEnum.KDC)
                .userId(userId)
                .patientId(request.getPatientId())
                .build());


        // build inputs & call to Flask service for disease classification
        KDCModelInputRequestDTO data = buildInputDataModel(request);
        KDCResultEnum result = KDCResultEnum.from(kdcModelService.predictDisease(data));
        log.info("KDC - Diagnose patient with data: {} and result: {}", data, result);

        // save to kdcExamResultRepository
        examResultRepository.save(KDCExamResultEntity.builder()
                .examinationId(examinationId)
                .wbc(data.getWBC())
                .ly(data.getLY())
                .ne(data.getNE())
                .rbc(data.getRBC())
                .hgb(data.getHGB())
                .hct(data.getHCT())
                .plt(data.getPLT())
                .na(data.getNa())
                .k(data.getK())
                .totalProtein(data.getTotalProtein())
                .albumin(data.getAlbumin())
                .ure(data.getUre())
                .creatinin(data.getCreatinin())
                .result(result)
                .build());

        return KDCDiagnoseResponseDTO.builder()
                .patientId(patientId)
                .examinationId(examinationId)
                .result(result)
                .build();
    }

    private KDCModelInputRequestDTO buildInputDataModel(KDCRequestDTO req) {
        // if not linguistic domain -> throw
        if (!isReqDataValid(req))
            throw new InternalException("KDC input type must be enum STRING or DOUBLE");

        PatientEntity patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new NotFoundException(PatientEntity.class, req.getPatientId()));

        // traverse fields, check type and set fields corresponding to type
        return KDCModelInputRequestDTO.builder()
                .age(Common.getDiffYears(patient.getBirthDate(), new Date()))
                .gender(patient.getGender().getValue())
                .WBC(req.getWBC() instanceof Number ? // check if number, then check if double or integer
                        (req.getWBC() instanceof Double ?
                                (Double) req.getWBC() :
                                (Integer) req.getWBC()) :
                        convertToClassicValue(
                                KDCDomainEnum.WBC,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getWBC())
                        ))
                .LY(req.getLY() instanceof Number ?
                        (req.getLY() instanceof Double ?
                                (Double) req.getLY() :
                                (Integer) req.getLY()) :
                        convertToClassicValue(
                                KDCDomainEnum.LY,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getLY())
                        ))
                .NE(req.getNE() instanceof Number ?
                        (req.getNE() instanceof Double ?
                                (Double) req.getNE() :
                                (Integer) req.getNE()) :
                        convertToClassicValue(
                                KDCDomainEnum.NE,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getNE())
                        ))
                .RBC(req.getRBC() instanceof Number ?
                        (req.getRBC() instanceof Double ?
                                (Double) req.getRBC() :
                                (Integer) req.getRBC()) :
                        convertToClassicValue(
                                KDCDomainEnum.RBC,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getRBC())
                        ))
                .HGB(req.getHGB() instanceof Number ?
                        (req.getHGB() instanceof Double ?
                                (Double) req.getHGB() :
                                (Integer) req.getHGB()) :
                        convertToClassicValue(
                                KDCDomainEnum.HGB,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getHGB())
                        ))
                .HCT(req.getHCT() instanceof Number ?
                        (req.getHCT() instanceof Double ?
                                (Double) req.getHCT() :
                                (Integer) req.getHCT()) :
                        convertToClassicValue(
                                KDCDomainEnum.HCT,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getHCT())
                        ))
                .PLT(req.getPLT() instanceof Number ?
                        (req.getPLT() instanceof Double ?
                                (Double) req.getPLT() :
                                (Integer) req.getPLT()) :
                        convertToClassicValue(
                                KDCDomainEnum.PLT,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getPLT())
                        ))
                .Na(req.getNa() instanceof Number ?
                        (req.getNa() instanceof Double ?
                                (Double) req.getNa() :
                                (Integer) req.getNa()) :
                        convertToClassicValue(
                                KDCDomainEnum.NA,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getNa())
                        ))
                .K(req.getK() instanceof Number ?
                        (req.getK() instanceof Double ?
                                (Double) req.getK() :
                                (Integer) req.getK()) :
                        convertToClassicValue(
                                KDCDomainEnum.K,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getK())
                        ))
                .totalProtein(req.getTotalProtein() instanceof Number ?
                        (req.getTotalProtein() instanceof Double ?
                                (Double) req.getTotalProtein() :
                                (Integer) req.getTotalProtein()) :
                        convertToClassicValue(
                                KDCDomainEnum.TOTAL_PROTEIN,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC,
                                        (String) req.getTotalProtein())
                        ))
                .Albumin(req.getAlbumin() instanceof Number ?
                        (req.getAlbumin() instanceof Double ?
                                (Double) req.getAlbumin() :
                                (Integer) req.getAlbumin()) :
                        convertToClassicValue(
                                KDCDomainEnum.ALBUMIN,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC,
                                        (String) req.getAlbumin())
                        ))
                .Ure(req.getUre() instanceof Number ?
                        (req.getUre() instanceof Double ?
                                (Double) req.getUre() :
                                (Integer) req.getUre()) :
                        convertToClassicValue(
                                KDCDomainEnum.URE,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC, (String) req.getUre())
                        ))
                .Creatinin(req.getCreatinin() instanceof Number ?
                        (req.getCreatinin() instanceof Double ?
                                (Double) req.getCreatinin() :
                                (Integer) req.getCreatinin()) :
                        convertToClassicValue(
                                KDCDomainEnum.CREATININ,
                                HAService.getVValueFromLinguistic(ApplicationEnum.KDC,
                                        (String) req.getCreatinin())
                        ))
                .build();
    }

    /**
     * convert v_value in range [0,1] to classic value using kdc_domain
     */
    private Double convertToClassicValue(KDCDomainEnum domainEnum, Double vValue) {
        // get classic set domain
        KDCDomainEntity domain = kdcDomainRepository.findById(domainEnum)
                .orElseThrow(() -> new NotFoundException(LinguisticDomainEntity.class, domainEnum.name()));

        // get max linguistic
        LinguisticDomainEntity maxLinguisticEntity = linguisticDomRepo.findById(
                        new LinguisticApplicationEntityKey(ApplicationEnum.KDC, LinguisticDomainEnum.VERY_HIGH))
                .orElseThrow(() -> new NotFoundException(LinguisticDomainEntity.class,
                        LinguisticDomainEnum.VERY_HIGH.getValue()));

        return Common.round(vValue * domain.getMaxValue() / maxLinguisticEntity.getVValue(), 2);
    }

    // TODO: check valid linguistic input
    private boolean isReqDataValid(KDCRequestDTO req) {
        return (req.getWBC() instanceof Number ||req.getWBC() instanceof String) &&
                (req.getLY() instanceof Number || req.getLY() instanceof String) &&
                (req.getNE() instanceof Number || req.getNE() instanceof String) &&
                (req.getRBC() instanceof Number || req.getRBC() instanceof String) &&
                (req.getHGB() instanceof Number || req.getHGB() instanceof String) &&
                (req.getHCT() instanceof Number || req.getHCT() instanceof String) &&
                (req.getPLT() instanceof Number || req.getPLT() instanceof String) &&
                (req.getNa() instanceof Number || req.getNa() instanceof String) &&
                (req.getK() instanceof Number || req.getK() instanceof String) &&
                (req.getTotalProtein() instanceof Number || req.getTotalProtein() instanceof String) &&
                (req.getAlbumin() instanceof Number || req.getAlbumin() instanceof String) &&
                (req.getUre() instanceof Number || req.getUre() instanceof String) &&
                (req.getCreatinin() instanceof Number || req.getCreatinin() instanceof String);
    }
}

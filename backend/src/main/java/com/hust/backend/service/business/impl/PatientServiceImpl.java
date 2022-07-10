package com.hust.backend.service.business.impl;

import com.hust.backend.config.AppConfig;
import com.hust.backend.constant.ResourceType;
import com.hust.backend.dto.request.PatientRegisterRequestDTO;
import com.hust.backend.dto.response.PatientInfoResponseDTO;
import com.hust.backend.entity.PatientEntity;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.repository.PatientRepository;
import com.hust.backend.service.business.PatientService;
import com.hust.backend.service.storage.StorageService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import com.hust.backend.utils.ULID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final StorageService storageService;
    private final AppConfig appConfig;

    public PatientServiceImpl(PatientRepository patientRepository,
                              StorageService storageService,
                              AppConfig appConfig) {
        this.patientRepository = patientRepository;
        this.storageService = storageService;
        this.appConfig = appConfig;
    }

    @Override
    public PagingInfo<PatientInfoResponseDTO> getALlPatients(String query, String gender, Pageable pageable) {
//        Page<PatientEntity> patientEntityPage = StringUtils.isBlank(query) ?
//                patientRepository.findAll(pageable) :
//                getAllPatientsByQuery(query, pageable);
        Page<PatientEntity> patientEntityPage = patientRepository.findAllPatients(query, gender, pageable);
        List<PatientInfoResponseDTO> results = Transformer.listToList(
                patientEntityPage.getContent(),
                patientEntity -> Common.convertObject(patientEntity, PatientInfoResponseDTO.class));

        return PagingInfo.<PatientInfoResponseDTO>builder()
                .items(results)
                .currentPage(patientEntityPage.getNumber() + 1)
                .totalItems(patientEntityPage.getTotalElements())
                .totalPages(patientEntityPage.getTotalPages())
                .pageSize(patientEntityPage.getSize())
                .build();
    }

    private Page<PatientEntity> getAllPatientsByQuery(String query, Pageable pageable) {
        return patientRepository.findByNameContainingOrEmailContainingOrPhoneNoContaining(query, query, query, pageable);
    }

    @Override
    public PatientInfoResponseDTO getPatient(String patientId) {
        PatientEntity patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException(PatientEntity.class, patientId));
        return Common.convertObject(patient, PatientInfoResponseDTO.class);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void registerPatient(PatientRegisterRequestDTO request) {
        PatientEntity patientEntity = PatientEntity.builder()
                .id(StringUtils.isBlank(request.getId()) ? ULID.nextULID() : request.getId())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .phoneNo(request.getPhoneNo())
                .email(request.getEmail())
                .build();

        if (request.getAvatar() != null) {
            String relAvatarUrl = storageService.upload(ResourceType.PATIENT, request.getAvatar());
            String absAvatarUrl = appConfig.getDomain() + "/media/" + relAvatarUrl;
            if (patientEntity.getAvatarUrl() != null) {
                String folder = "/" + ResourceType.PATIENT.folderName + "/";
                String avatarFileName = folder + patientEntity.getAvatarUrl().split(folder)[1];
                if (storageService.isExist(avatarFileName)) {
                    storageService.delete(avatarFileName);
                }
            }
            patientEntity.setAvatarUrl(absAvatarUrl);
        }
        patientRepository.save(patientEntity);
        log.info("New patient registered successfully {}", patientEntity);
    }

}

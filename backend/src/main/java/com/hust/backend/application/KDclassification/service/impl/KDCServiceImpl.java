package com.hust.backend.application.KDclassification.service.impl;

import com.hust.backend.application.KDclassification.dto.response.KDCDomainResponseDTO;
import com.hust.backend.application.KDclassification.entity.KDCDomainEntity;
import com.hust.backend.application.KDclassification.repository.KDCDomainRepository;
import com.hust.backend.application.KDclassification.service.KDCService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KDCServiceImpl implements KDCService {
    private final KDCDomainRepository kdcDomainRepository;

    public KDCServiceImpl(KDCDomainRepository kdcDomainRepository) {
        this.kdcDomainRepository = kdcDomainRepository;
    }

    @Override
    public List<KDCDomainResponseDTO> getAllKDCDomain() {
        List<KDCDomainEntity> allDomainEntities = kdcDomainRepository.findAll();
        return Transformer.listToList(
                allDomainEntities,
                kdcDomainEntity -> Common.convertObject(kdcDomainEntity, KDCDomainResponseDTO.class));
    }
}

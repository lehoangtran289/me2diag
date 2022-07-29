package com.hust.backend.application.KDclassification.service.impl;

import com.hust.backend.application.KDclassification.constant.KDCDomainEnum;
import com.hust.backend.application.KDclassification.dto.request.KDCDomainConfigRequestDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCDomainResponseDTO;
import com.hust.backend.application.KDclassification.entity.KDCDomainEntity;
import com.hust.backend.application.KDclassification.repository.KDCDomainRepository;
import com.hust.backend.application.KDclassification.service.KDCConfigService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class KDCConfigServiceImpl implements KDCConfigService {
    private final KDCDomainRepository kdcDomainRepository;

    public KDCConfigServiceImpl(KDCDomainRepository kdcDomainRepository) {
        this.kdcDomainRepository = kdcDomainRepository;
    }

    @Override
    public List<KDCDomainResponseDTO> getAllKDCDomain() {
        List<KDCDomainEntity> allDomainEntities = kdcDomainRepository.findAll();
        return Transformer.listToList(
                allDomainEntities,
                kdcDomainEntity -> Common.convertObject(kdcDomainEntity, KDCDomainResponseDTO.class));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean changeKDCDomainConfigs(List<KDCDomainConfigRequestDTO> request) {
        if (request.size() == 0) return false;
        List<KDCDomainEnum> keys = Transformer.listToList(request, KDCDomainConfigRequestDTO::getName);
        List<KDCDomainEntity> entitiesToBeChanged = kdcDomainRepository.findAllById(keys);
        for (KDCDomainEntity entity : entitiesToBeChanged) {
            KDCDomainConfigRequestDTO match = request.stream()
                    .filter(e -> e.getName().equals(entity.getName()))
                    .findAny().orElse(null);
            if (match != null) {
                entity.setMaxValue(match.getMaxValue());
                entity.setMinValue(match.getMinValue());
            }
        }
        return true;
    }
}

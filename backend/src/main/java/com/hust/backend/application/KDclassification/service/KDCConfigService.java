package com.hust.backend.application.KDclassification.service;

import com.hust.backend.application.KDclassification.dto.request.KDCDomainConfigRequestDTO;
import com.hust.backend.application.KDclassification.dto.response.KDCDomainResponseDTO;

import java.util.List;

public interface KDCConfigService {
    List<KDCDomainResponseDTO> getAllKDCDomain();

    Boolean changeKDCDomainConfigs(List<KDCDomainConfigRequestDTO> request);
}

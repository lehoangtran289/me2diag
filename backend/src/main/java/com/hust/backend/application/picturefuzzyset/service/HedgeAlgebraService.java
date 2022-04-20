package com.hust.backend.application.picturefuzzyset.service;

import com.hust.backend.application.picturefuzzyset.dto.request.HedgeAlgebraConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.LinguisticDomainDTO;

import java.util.List;

public interface HedgeAlgebraService {
    List<LinguisticDomainDTO> getAllLinguisticDomainElements();

    List<LinguisticDomainDTO> changeHedgeAlgebraConfigs(HedgeAlgebraConfigRequestDTO request);

}

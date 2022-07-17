package com.hust.backend.service.business;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.dto.request.HedgeAlgebraConfigRequestDTO;
import com.hust.backend.dto.response.HedgeConfigResponseDTO;
import com.hust.backend.dto.response.LinguisticDomainResponseDTO;

import java.util.List;

public interface HedgeAlgebraService {
    Double getVValueFromLinguistic(ApplicationEnum appId, String linguisticValue);

    List<LinguisticDomainResponseDTO> getAllLinguisticDomainElements(ApplicationEnum appId);

    List<LinguisticDomainResponseDTO> changeHedgeAlgebraConfigs(HedgeAlgebraConfigRequestDTO request);

    List<HedgeConfigResponseDTO> getAllHedgeConfigsElements(ApplicationEnum appId);
}

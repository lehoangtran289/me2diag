package com.hust.backend.application.picturefuzzyset.service.impl;

import com.hust.backend.application.picturefuzzyset.dto.request.SymptomDiagnoseConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.entity.SymptomDiagnoseEntity;
import com.hust.backend.application.picturefuzzyset.model.SymptomDiagnoseConfig;
import com.hust.backend.application.picturefuzzyset.repository.SymptomDiagnoseRepository;
import com.hust.backend.application.picturefuzzyset.service.PFSConfigService;
import com.hust.backend.application.picturefuzzyset.utils.PFSCommon;
import com.hust.backend.exception.InternalException;
import com.hust.backend.service.business.HedgeAlgebraService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PFSConfigServiceImpl implements PFSConfigService {
    private final SymptomDiagnoseRepository symptomDiagnoseRepo;

    public PFSConfigServiceImpl(SymptomDiagnoseRepository symptomDiagnoseRepo,
                                HedgeAlgebraService haService) {
        this.symptomDiagnoseRepo = symptomDiagnoseRepo;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean changePFSConfigs(List<SymptomDiagnoseConfigRequestDTO> request) {
        List<SymptomDiagnoseEntity> updatedPFSConfigs = new ArrayList<>();
        for (SymptomDiagnoseConfigRequestDTO config : request) {
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

}

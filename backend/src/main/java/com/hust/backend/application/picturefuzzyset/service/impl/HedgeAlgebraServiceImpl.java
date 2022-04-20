package com.hust.backend.application.picturefuzzyset.service.impl;

import com.hust.backend.application.picturefuzzyset.constant.HedgeAlgebraEnum;
import com.hust.backend.application.picturefuzzyset.constant.LinguisticDomainEnum;
import com.hust.backend.application.picturefuzzyset.dto.request.HedgeAlgebraConfigRequestDTO;
import com.hust.backend.application.picturefuzzyset.dto.response.LinguisticDomainDTO;
import com.hust.backend.application.picturefuzzyset.entity.HedgeAlgebraEntity;
import com.hust.backend.application.picturefuzzyset.entity.LinguisticDomainEntity;
import com.hust.backend.application.picturefuzzyset.repository.HedgeAlgebraConfigRepository;
import com.hust.backend.application.picturefuzzyset.repository.LinguisticDomainRepository;
import com.hust.backend.application.picturefuzzyset.service.HedgeAlgebraService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
@Slf4j
public class HedgeAlgebraServiceImpl implements HedgeAlgebraService {
    private final HedgeAlgebraConfigRepository hedgeAlgebraConfigRepo;
    private final LinguisticDomainRepository linguisticDomainRepo;

    public HedgeAlgebraServiceImpl(HedgeAlgebraConfigRepository hedgeAlgebraConfigRepo,
                                   LinguisticDomainRepository linguisticDomainRepo) {
        this.hedgeAlgebraConfigRepo = hedgeAlgebraConfigRepo;
        this.linguisticDomainRepo = linguisticDomainRepo;
    }

    @Override
    public List<LinguisticDomainDTO> getAllLinguisticDomainElements() {
        return Transformer.listToList(
                linguisticDomainRepo.findAll(),
                linguisticDomainEntity -> Common.convertObject(linguisticDomainEntity,
                        LinguisticDomainDTO.class)
        );
    }

    // FIXME: hardcode a lot :<
    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<LinguisticDomainDTO> changeHedgeAlgebraConfigs(HedgeAlgebraConfigRequestDTO request) {
        double alpha = request.getAlpha();
        double theta = request.getNeutralTheta();

        // cache calculated fm values (to avoid db query)
        Map<HedgeAlgebraEnum, Double> fm = new HashMap<>(Map.ofEntries(
                entry(HedgeAlgebraEnum.SLIGHTLY, 0.0),
                entry(HedgeAlgebraEnum.VERY, 0.0),
                entry(HedgeAlgebraEnum.MEDIUM, 0.0),
                entry(HedgeAlgebraEnum.LOW, 0.0),
                entry(HedgeAlgebraEnum.HIGH, 0.0)
        ));

        // hedge_algebra_config
        List<HedgeAlgebraEntity> hedgeAlgebrasToBeChanged = hedgeAlgebraConfigRepo.findAllByHedgeAlgebraEnumIn(
                Arrays.asList(HedgeAlgebraEnum.SLIGHTLY,
                        HedgeAlgebraEnum.VERY,
                        HedgeAlgebraEnum.MEDIUM,
                        HedgeAlgebraEnum.LOW,
                        HedgeAlgebraEnum.HIGH
                ));
        for (HedgeAlgebraEntity entity : hedgeAlgebrasToBeChanged) {
            switch (entity.getHedgeAlgebraEnum()) {
                case SLIGHTLY:
                    entity.setFmValue(alpha);
                    fm.put(HedgeAlgebraEnum.SLIGHTLY, entity.getFmValue());
                    break;
                case LOW:
                    entity.setFmValue(alpha);
                    fm.put(HedgeAlgebraEnum.LOW, entity.getFmValue());
                    break;
                case VERY:
                    entity.setFmValue(1 - alpha);
                    fm.put(HedgeAlgebraEnum.VERY, entity.getFmValue());
                    break;
                case HIGH:
                    entity.setFmValue(1 - alpha);
                    fm.put(HedgeAlgebraEnum.HIGH, entity.getFmValue());
                    break;
                case MEDIUM:
                    entity.setFmValue(theta);
                    fm.put(HedgeAlgebraEnum.MEDIUM, entity.getFmValue());
                    break;
            }
        }
        hedgeAlgebraConfigRepo.saveAll(hedgeAlgebrasToBeChanged);

        // cache calculated v values (to avoid db query)
        Map<LinguisticDomainEnum, Double> v = new HashMap<>(Map.ofEntries(
                entry(LinguisticDomainEnum.VERY_HIGH, 0.0),
                entry(LinguisticDomainEnum.HIGH, 0.0),
                entry(LinguisticDomainEnum.SLIGHTLY_HIGH, 0.0),
                entry(LinguisticDomainEnum.MEDIUM, 0.0),
                entry(LinguisticDomainEnum.SLIGHTLY_LOW, 0.0),
                entry(LinguisticDomainEnum.LOW, 0.0),
                entry(LinguisticDomainEnum.VERY_LOW, 0.0)
        ));

        // linguistic_domain
        List<LinguisticDomainEntity> linguisticToBeChanged = linguisticDomainRepo.findAllByLinguisticDomainElementIn(
                Arrays.asList(
                        LinguisticDomainEnum.VERY_HIGH,
                        LinguisticDomainEnum.HIGH,
                        LinguisticDomainEnum.SLIGHTLY_HIGH,
                        LinguisticDomainEnum.MEDIUM,
                        LinguisticDomainEnum.SLIGHTLY_LOW,
                        LinguisticDomainEnum.LOW,
                        LinguisticDomainEnum.VERY_LOW
                ));
        for (LinguisticDomainEntity entity : linguisticToBeChanged) {
            switch (entity.getLinguisticDomainElement()) {
                case LOW:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.LOW));
                    entity.setVValue(theta - alpha * fm.get(HedgeAlgebraEnum.LOW));
                    v.put(LinguisticDomainEnum.LOW, entity.getVValue());
                    break;

                case MEDIUM:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.MEDIUM));
                    entity.setVValue(theta);
                    break;

                case HIGH:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.HIGH));
                    entity.setVValue(theta + alpha * fm.get(HedgeAlgebraEnum.LOW));
                    v.put(LinguisticDomainEnum.HIGH, entity.getVValue());
                    break;

                case VERY_HIGH:
                    double fmVeryHigh = fm.get(HedgeAlgebraEnum.VERY) * fm.get(HedgeAlgebraEnum.HIGH);
                    double vVeryHigh = theta + alpha * fm.get(HedgeAlgebraEnum.HIGH) + //vLow
                            1 * (fmVeryHigh - 0.5 * (1 - 1 * 1 * (1 - alpha - alpha)) * fmVeryHigh);
                    entity.setFmValue(fmVeryHigh);
                    entity.setVValue(vVeryHigh);
                    break;

                case SLIGHTLY_HIGH:
                    double fmSlightlyHigh = fm.get(HedgeAlgebraEnum.SLIGHTLY) * fm.get(HedgeAlgebraEnum.HIGH);
                    double vSlightlyHigh = theta + alpha * fm.get(HedgeAlgebraEnum.HIGH) + //vHigh
                            (-1) * (fmSlightlyHigh - 0.5 * (1 - (-1) * (-1) * (1 - alpha - alpha)) * fmSlightlyHigh);
                    entity.setFmValue(fmSlightlyHigh);
                    entity.setVValue(vSlightlyHigh);
                    break;

                case SLIGHTLY_LOW:
                    double fmSlightlyLow = fm.get(HedgeAlgebraEnum.SLIGHTLY) * fm.get(HedgeAlgebraEnum.LOW);
                    double vSlightlyLow = theta - alpha * fm.get(HedgeAlgebraEnum.LOW) + //vLow
                            1 * (fmSlightlyLow - 0.5 * (1 - 1 * 1 * (1 - alpha - alpha)) * fmSlightlyLow);
                    entity.setFmValue(fmSlightlyLow);
                    entity.setVValue(vSlightlyLow);
                    break;

                case VERY_LOW:
                    double fmVeryLow = fm.get(HedgeAlgebraEnum.VERY) * fm.get(HedgeAlgebraEnum.LOW);
                    double vVeryLow = theta - alpha * fm.get(HedgeAlgebraEnum.LOW) + //vLow
                            (-1) * (fmVeryLow - 0.5 * (1 - (-1) * (-1) * (1 - alpha - alpha)) * fmVeryLow);
                    entity.setFmValue(fmVeryLow);
                    entity.setVValue(vVeryLow);
                    break;
            }
        }
        hedgeAlgebraConfigRepo.saveAll(hedgeAlgebrasToBeChanged);

        return Transformer.listToList(
                linguisticToBeChanged,
                linguisticDomainEntity -> Common.convertObject(linguisticDomainEntity,
                        LinguisticDomainDTO.class)
        );
    }
}

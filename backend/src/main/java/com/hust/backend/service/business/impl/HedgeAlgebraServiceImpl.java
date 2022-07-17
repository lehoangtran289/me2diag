package com.hust.backend.service.business.impl;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.HedgeAlgebraEnum;
import com.hust.backend.constant.LinguisticDomainEnum;
import com.hust.backend.dto.request.HedgeAlgebraConfigRequestDTO;
import com.hust.backend.dto.response.HedgeConfigResponseDTO;
import com.hust.backend.dto.response.LinguisticDomainResponseDTO;
import com.hust.backend.entity.HedgeAlgebraEntity;
import com.hust.backend.entity.LinguisticDomainEntity;
import com.hust.backend.entity.key.LinguisticApplicationEntityKey;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.repository.HedgeAlgebraConfigRepository;
import com.hust.backend.repository.LinguisticDomainRepository;
import com.hust.backend.service.business.HedgeAlgebraService;
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

// FIXME: hardcode a lot huhu :<
//TODO: uncomment repo saveAll
@Service
@Slf4j
public class HedgeAlgebraServiceImpl implements HedgeAlgebraService {
    private final HedgeAlgebraConfigRepository hedgeAlgebraConfigRepo;
    private final LinguisticDomainRepository linguisticDomRepo;

    public HedgeAlgebraServiceImpl(HedgeAlgebraConfigRepository hedgeAlgebraConfigRepo,
                                   LinguisticDomainRepository linguisticDomRepo) {
        this.hedgeAlgebraConfigRepo = hedgeAlgebraConfigRepo;
        this.linguisticDomRepo = linguisticDomRepo;
    }

    @Override
    public Double getVValueFromLinguistic(ApplicationEnum appId, String linguisticValue) {
        LinguisticDomainEnum linguisticDomainEnum = LinguisticDomainEnum.from(linguisticValue);
        LinguisticDomainEntity linguisticEntity = linguisticDomRepo.findById(
                        new LinguisticApplicationEntityKey(appId, linguisticDomainEnum))
                .orElseThrow(() -> new NotFoundException(LinguisticDomainEntity.class, linguisticValue));
        return linguisticEntity.getVValue();
    }

    @Override
    public List<HedgeConfigResponseDTO> getAllHedgeConfigsElements(ApplicationEnum appId) {
        return Transformer.listToList(
                hedgeAlgebraConfigRepo.findAllByApplicationIdOrderByLinguisticOrderAsc(appId),
                hedgeAlgebraEntity -> Common.convertObject(hedgeAlgebraEntity, HedgeConfigResponseDTO.class)
        );
    }

    @Override
    public List<LinguisticDomainResponseDTO> getAllLinguisticDomainElements(ApplicationEnum appId) {
        return Transformer.listToList(
                linguisticDomRepo.findAllByApplicationIdOrderByLinguisticOrderAsc(appId),
                linguisticDomainEntity -> Common.convertObject(linguisticDomainEntity,
                        LinguisticDomainResponseDTO.class)
        );
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<LinguisticDomainResponseDTO> changeHedgeAlgebraConfigs(HedgeAlgebraConfigRequestDTO request) {
        switch (request.getAppId()) {
            case PFS:
                return changeHedgeAlgebraConfigsPFS(request.getConfigs(), request.getNeutralTheta());
            case KDC:
                return changeHedgeAlgebraConfigsKDC(request.getConfigs(), request.getNeutralTheta());
            default:
                log.error("Wrong appId {}", request.getAppId());
                throw new NotFoundException(ApplicationEnum.class, request.getAppId().getValue());
        }
    }

    private List<LinguisticDomainResponseDTO> changeHedgeAlgebraConfigsPFS(Map<HedgeAlgebraEnum, Double> hedgeConfigs, Double theta) {

        // cache calculated fm values (to avoid db query)
        Map<HedgeAlgebraEnum, Double> fm = new HashMap<>(Map.ofEntries(
                entry(HedgeAlgebraEnum.SLIGHTLY, 0.0),
                entry(HedgeAlgebraEnum.VERY, 0.0),
                entry(HedgeAlgebraEnum.MEDIUM, 0.0),
                entry(HedgeAlgebraEnum.LOW, 0.0),
                entry(HedgeAlgebraEnum.HIGH, 0.0)
        ));

        // hedge_algebra_config
        List<HedgeAlgebraEntity> hedgeAlgebrasToBeChanged =
                hedgeAlgebraConfigRepo.findAllByApplicationIdAndHedgeAlgebraEnumIn(
                        ApplicationEnum.PFS,
                        Arrays.asList(HedgeAlgebraEnum.SLIGHTLY,
                                HedgeAlgebraEnum.VERY,
                                HedgeAlgebraEnum.MEDIUM,
                                HedgeAlgebraEnum.LOW,
                                HedgeAlgebraEnum.HIGH
                        ));

        // in this case the hedgeConfig map has only 1 alpha element SLIGHTLY
        double alpha = hedgeConfigs.get(HedgeAlgebraEnum.SLIGHTLY);
        for (HedgeAlgebraEntity entity : hedgeAlgebrasToBeChanged) {
            switch (entity.getHedgeAlgebraEnum()) {
                case SLIGHTLY:
                    entity.setFmValue(alpha);
                    fm.put(HedgeAlgebraEnum.SLIGHTLY, entity.getFmValue());
                    break;
                case VERY:
                    entity.setFmValue(1 - alpha);
                    fm.put(HedgeAlgebraEnum.VERY, entity.getFmValue());
                    break;
                case LOW:
                    entity.setFmValue(theta);
                    fm.put(HedgeAlgebraEnum.LOW, entity.getFmValue());
                    break;
                case HIGH:
                    entity.setFmValue(1 - theta);
                    fm.put(HedgeAlgebraEnum.HIGH, entity.getFmValue());
                    break;
                case MEDIUM:
                    entity.setFmValue(theta);
                    fm.put(HedgeAlgebraEnum.MEDIUM, entity.getFmValue());
                    break;
            }
        }
//        hedgeAlgebraConfigRepo.saveAll(hedgeAlgebrasToBeChanged);

        // linguistic_domain
        List<LinguisticDomainEntity> linguisticToBeChanged =
                linguisticDomRepo.findAllByApplicationIdAndLinguisticDomainElementIn(
                        ApplicationEnum.PFS,
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
                    break;

                case MEDIUM:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.MEDIUM));
                    entity.setVValue(theta);
                    break;

                case HIGH:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.HIGH));
                    entity.setVValue(theta + alpha * fm.get(HedgeAlgebraEnum.LOW));
                    break;

                case VERY_HIGH:
                    double fmVeryHigh = fm.get(HedgeAlgebraEnum.VERY) * fm.get(HedgeAlgebraEnum.HIGH);
                    double vVeryHigh = computeVValue(theta, alpha, 1, fm.get(HedgeAlgebraEnum.HIGH),
                            fmVeryHigh, 1, -1);
                    entity.setFmValue(fmVeryHigh);
                    entity.setVValue(vVeryHigh);
                    break;

                case SLIGHTLY_HIGH:
                    double fmSlightlyHigh = fm.get(HedgeAlgebraEnum.SLIGHTLY) * fm.get(HedgeAlgebraEnum.HIGH);
                    double vSlightlyHigh = computeVValue(theta, alpha, 1, fm.get(HedgeAlgebraEnum.HIGH),
                            fmSlightlyHigh, -1, -1);
                    entity.setFmValue(fmSlightlyHigh);
                    entity.setVValue(vSlightlyHigh);
                    break;

                case SLIGHTLY_LOW:
                    double fmSlightlyLow = fm.get(HedgeAlgebraEnum.SLIGHTLY) * fm.get(HedgeAlgebraEnum.LOW);
                    double vSlightlyLow = computeVValue(theta, alpha, -1, fm.get(HedgeAlgebraEnum.LOW),
                            fmSlightlyLow, 1, 1);
                    entity.setFmValue(fmSlightlyLow);
                    entity.setVValue(vSlightlyLow);
                    break;

                case VERY_LOW:
                    double fmVeryLow = fm.get(HedgeAlgebraEnum.VERY) * fm.get(HedgeAlgebraEnum.LOW);
                    double vVeryLow = computeVValue(theta, alpha, -1, fm.get(HedgeAlgebraEnum.LOW), fmVeryLow
                            , -1, 1);
                    entity.setFmValue(fmVeryLow);
                    entity.setVValue(vVeryLow);
                    break;
            }
        }
//        linguisticDomainRepo.saveAll(linguisticToBeChanged);

        return Transformer.listToList(
                linguisticToBeChanged,
                linguisticDomainEntity -> Common.convertObject(linguisticDomainEntity,
                        LinguisticDomainResponseDTO.class)
        );
    }

    // TODO huhu :<
    private List<LinguisticDomainResponseDTO> changeHedgeAlgebraConfigsKDC(Map<HedgeAlgebraEnum, Double> hedgeConfigs, Double theta) {
        double alpha = hedgeConfigs.get(HedgeAlgebraEnum.LITTLE) + hedgeConfigs.get(HedgeAlgebraEnum.POSSIBLE);

        // cache calculated fm values (to avoid db query)
        Map<HedgeAlgebraEnum, Double> fm = new HashMap<>(Map.ofEntries(
                entry(HedgeAlgebraEnum.MORE, 0.0),
                entry(HedgeAlgebraEnum.VERY, 0.0),
                entry(HedgeAlgebraEnum.POSSIBLE, 0.0),
                entry(HedgeAlgebraEnum.LITTLE, 0.0),
                entry(HedgeAlgebraEnum.MEDIUM, 0.0),
                entry(HedgeAlgebraEnum.LOW, 0.0),
                entry(HedgeAlgebraEnum.HIGH, 0.0)
        ));

        // hedge_algebra_config
        List<HedgeAlgebraEntity> hedgeAlgebrasToBeChanged =
                hedgeAlgebraConfigRepo.findAllByApplicationIdAndHedgeAlgebraEnumIn(
                        ApplicationEnum.KDC,
                        Arrays.asList(
                                HedgeAlgebraEnum.LITTLE,
                                HedgeAlgebraEnum.POSSIBLE,
                                HedgeAlgebraEnum.MORE,
                                HedgeAlgebraEnum.VERY,
                                HedgeAlgebraEnum.MEDIUM,
                                HedgeAlgebraEnum.LOW,
                                HedgeAlgebraEnum.HIGH
                        ));
        for (HedgeAlgebraEntity entity : hedgeAlgebrasToBeChanged) {
            switch (entity.getHedgeAlgebraEnum()) {
                case LITTLE:
                    entity.setFmValue(hedgeConfigs.get(HedgeAlgebraEnum.LITTLE));
                    fm.put(HedgeAlgebraEnum.LITTLE, entity.getFmValue());
                    break;
                case POSSIBLE:
                    entity.setFmValue(hedgeConfigs.get(HedgeAlgebraEnum.POSSIBLE));
                    fm.put(HedgeAlgebraEnum.POSSIBLE, entity.getFmValue());
                    break;
                case MORE:
                    entity.setFmValue(hedgeConfigs.get(HedgeAlgebraEnum.MORE));
                    fm.put(HedgeAlgebraEnum.MORE, entity.getFmValue());
                    break;
                case VERY:
                    entity.setFmValue(hedgeConfigs.get(HedgeAlgebraEnum.VERY));
                    fm.put(HedgeAlgebraEnum.VERY, entity.getFmValue());
                    break;
                case MEDIUM:
                    entity.setFmValue(theta);
                    fm.put(HedgeAlgebraEnum.MEDIUM, entity.getFmValue());
                    break;
                case LOW:
                    entity.setFmValue(theta);
                    fm.put(HedgeAlgebraEnum.LOW, entity.getFmValue());
                    break;
                case HIGH:
                    entity.setFmValue(1 - theta);
                    fm.put(HedgeAlgebraEnum.HIGH, entity.getFmValue());
                    break;
            }
        }
//        hedgeAlgebraConfigRepo.saveAll(hedgeAlgebrasToBeChanged);

        // linguistic_domain
        List<LinguisticDomainEntity> linguisticToBeChanged =
                linguisticDomRepo.findAllByApplicationIdAndLinguisticDomainElementIn(
                        ApplicationEnum.KDC,
                        Arrays.asList(
                                LinguisticDomainEnum.LITTLE_LOW,
                                LinguisticDomainEnum.POSSIBLE_LOW,
                                LinguisticDomainEnum.LOW,
                                LinguisticDomainEnum.MORE_LOW,
                                LinguisticDomainEnum.VERY_LOW,
                                LinguisticDomainEnum.MEDIUM,
                                LinguisticDomainEnum.LITTLE_HIGH,
                                LinguisticDomainEnum.POSSIBLE_HIGH,
                                LinguisticDomainEnum.HIGH,
                                LinguisticDomainEnum.MORE_HIGH,
                                LinguisticDomainEnum.VERY_HIGH
                        ));

        double fmLittleLow = fm.get(HedgeAlgebraEnum.LITTLE) * fm.get(HedgeAlgebraEnum.LOW);
        double fmPossibleLow = fm.get(HedgeAlgebraEnum.POSSIBLE) * fm.get(HedgeAlgebraEnum.LOW);
        double fmMoreLow = fm.get(HedgeAlgebraEnum.MORE) * fm.get(HedgeAlgebraEnum.LOW);
        double fmVeryLow = fm.get(HedgeAlgebraEnum.VERY) * fm.get(HedgeAlgebraEnum.LOW);
        double fmLittleHigh = fm.get(HedgeAlgebraEnum.LITTLE) * fm.get(HedgeAlgebraEnum.HIGH);
        double fmPossibleHigh = fm.get(HedgeAlgebraEnum.POSSIBLE) * fm.get(HedgeAlgebraEnum.HIGH);
        double fmMoreHigh = fm.get(HedgeAlgebraEnum.MORE) * fm.get(HedgeAlgebraEnum.HIGH);
        double fmVeryHigh = fm.get(HedgeAlgebraEnum.VERY) * fm.get(HedgeAlgebraEnum.HIGH);

        for (LinguisticDomainEntity entity : linguisticToBeChanged) {
            switch (entity.getLinguisticDomainElement()) {
                case LOW:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.LOW));
                    entity.setVValue(theta - alpha * fm.get(HedgeAlgebraEnum.LOW));
                    break;
                case MEDIUM:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.MEDIUM));
                    entity.setVValue(theta);
                    break;
                case HIGH:
                    entity.setFmValue(fm.get(HedgeAlgebraEnum.HIGH));
                    entity.setVValue(theta + alpha * fm.get(HedgeAlgebraEnum.HIGH));
                    break;

                case LITTLE_LOW:
                    double vLittleLow = computeVValue2(theta, alpha, -1, fm.get(HedgeAlgebraEnum.LOW),
                            Arrays.asList(fmPossibleLow, fmLittleLow), 1, 1);
                    entity.setFmValue(fmLittleLow);
                    entity.setVValue(vLittleLow);
                    break;
                case POSSIBLE_LOW:
                    double vPossibleLow = computeVValue2(theta, alpha, -1, fm.get(HedgeAlgebraEnum.LOW),
                            List.of(fmPossibleLow), 1, 1);
                    entity.setFmValue(fmPossibleLow);
                    entity.setVValue(vPossibleLow);
                    break;
                case MORE_LOW:
                    double vMoreLow = computeVValue2(theta, alpha, -1, fm.get(HedgeAlgebraEnum.LOW),
                            List.of(fmMoreLow), -1, 1);
                    entity.setFmValue(fmMoreLow);
                    entity.setVValue(vMoreLow);
                    break;
                case VERY_LOW:
                    double vVeryLow = computeVValue2(theta, alpha, -1, fm.get(HedgeAlgebraEnum.LOW),
                            Arrays.asList(fmVeryLow, fmMoreLow), -1, 1);
                    entity.setFmValue(fmVeryLow);
                    entity.setVValue(vVeryLow);
                    break;
                case LITTLE_HIGH:
                    double vLittleHigh = computeVValue2(theta, alpha, 1, fm.get(HedgeAlgebraEnum.HIGH),
                            Arrays.asList(fmPossibleHigh, fmLittleHigh), -1, -1);
                    entity.setFmValue(fmLittleHigh);
                    entity.setVValue(vLittleHigh);
                    break;
                case POSSIBLE_HIGH:
                    double vPossibleHigh = computeVValue2(theta, alpha, 1, fm.get(HedgeAlgebraEnum.HIGH),
                            List.of(fmPossibleHigh), -1, -1);
                    entity.setFmValue(fmPossibleHigh);
                    entity.setVValue(vPossibleHigh);
                    break;
                case MORE_HIGH:
                    double vMoreHigh = computeVValue2(theta, alpha, 1, fm.get(HedgeAlgebraEnum.HIGH),
                            List.of(fmMoreHigh), 1, -1);
                    entity.setFmValue(fmMoreHigh);
                    entity.setVValue(vMoreHigh);
                    break;
                case VERY_HIGH:
                    double vVeryHigh = computeVValue2(theta, alpha, 1, fm.get(HedgeAlgebraEnum.HIGH),
                            Arrays.asList(fmVeryHigh, fmMoreHigh), 1, -1);
                    entity.setFmValue(fmVeryHigh);
                    entity.setVValue(vVeryHigh);
                    break;
            }
        }
//        linguisticDomainRepo.saveAll(linguisticToBeChanged);

        return Transformer.listToList(
                linguisticToBeChanged,
                linguisticDomainEntity -> Common.convertObject(linguisticDomainEntity,
                        LinguisticDomainResponseDTO.class)
        );
    }


    /**
     * @param theta     fm(c-)
     * @param alpha     fm(h-)
     * @param cSign     sign(c+-)
     * @param fmC       fm(c+-)
     * @param fmDomain  fm(hj x)
     * @param hjxSign   sign(hj x)
     * @param h1hjxSign extended linguistic hedge h_1 = 'slightly'
     *                  => sign(extended hj x) = sign(slightly + hj + x)
     * @return computed v value
     */
    //TODO: to be replaced with computeVV2() below
    private double computeVValue(double theta, double alpha, int cSign, double fmC, double fmDomain,
                                 int hjxSign, int h1hjxSign) {
        return theta + cSign * alpha * fmC + //vLow
                hjxSign * (fmDomain - 0.5 * (1 - hjxSign * h1hjxSign * (1 - alpha - alpha)) * fmDomain);
    }

    // compute V Value when negative or positive hedges having >=2 elements. e.g: H+ = {more, very}
    private double computeVValue2(double theta, double alpha, int cSign, double fmC, List<Double> fmDomains,
                                 int hjxSign, int h1hjxSign) {
        double fmDomainSum = fmDomains.stream().mapToDouble(Double::doubleValue).sum();
        return theta + cSign * alpha * fmC + //vLow
                hjxSign * (fmDomainSum - 0.5 * (1 - hjxSign * h1hjxSign * (1 - alpha - alpha)) * fmDomainSum);
    }
}

package com.hust.backend.repository;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.LinguisticDomainEnum;
import com.hust.backend.entity.LinguisticDomainEntity;
import com.hust.backend.entity.key.LinguisticApplicationEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinguisticDomainRepository extends JpaRepository<LinguisticDomainEntity, LinguisticApplicationEntityKey> {
    List<LinguisticDomainEntity> findAllByApplicationIdAndLinguisticDomainElementIn(ApplicationEnum appId, List<LinguisticDomainEnum> ids);

    List<LinguisticDomainEntity> findAllByApplicationId(ApplicationEnum appId);
}

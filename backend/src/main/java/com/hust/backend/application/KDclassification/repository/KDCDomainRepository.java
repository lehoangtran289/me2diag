package com.hust.backend.application.KDclassification.repository;

import com.hust.backend.application.KDclassification.constant.KDCDomainEnum;
import com.hust.backend.application.KDclassification.entity.KDCDomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDCDomainRepository extends JpaRepository<KDCDomainEntity, KDCDomainEnum> {
}

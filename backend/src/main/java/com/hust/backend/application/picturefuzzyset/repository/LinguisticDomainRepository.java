package com.hust.backend.application.picturefuzzyset.repository;

import com.hust.backend.application.picturefuzzyset.constant.LinguisticDomainEnum;
import com.hust.backend.application.picturefuzzyset.entity.LinguisticDomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinguisticDomainRepository extends JpaRepository<LinguisticDomainEntity, LinguisticDomainEnum> {
}

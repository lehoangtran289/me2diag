package com.hust.backend.application.picturefuzzyset.repository;

import com.hust.backend.application.picturefuzzyset.constant.LinguisticDomainEnum;
import com.hust.backend.application.picturefuzzyset.entity.LinguisticDomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HedgeAlgebraConfigRepository extends JpaRepository<LinguisticDomainEntity, LinguisticDomainEnum> {

}

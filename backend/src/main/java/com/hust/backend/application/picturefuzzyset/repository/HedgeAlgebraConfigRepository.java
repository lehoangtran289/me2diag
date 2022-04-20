package com.hust.backend.application.picturefuzzyset.repository;

import com.hust.backend.application.picturefuzzyset.constant.HedgeAlgebraEnum;
import com.hust.backend.application.picturefuzzyset.entity.HedgeAlgebraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HedgeAlgebraConfigRepository extends JpaRepository<HedgeAlgebraEntity, HedgeAlgebraEnum> {
    List<HedgeAlgebraEntity> findAllByHedgeAlgebraEnumIn(List<HedgeAlgebraEnum> ids);
}

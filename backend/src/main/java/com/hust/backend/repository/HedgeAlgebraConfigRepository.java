package com.hust.backend.repository;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.HedgeAlgebraEnum;
import com.hust.backend.entity.HedgeAlgebraEntity;
import com.hust.backend.entity.key.HedgeApplicationEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HedgeAlgebraConfigRepository extends JpaRepository<HedgeAlgebraEntity, HedgeApplicationEntityKey> {
    List<HedgeAlgebraEntity> findAllByApplicationIdAndHedgeAlgebraEnumIn(ApplicationEnum appId, List<HedgeAlgebraEnum> ids);
}

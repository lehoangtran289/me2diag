package com.hust.backend.repository;

import com.hust.backend.constant.DiagnoseEnum;
import com.hust.backend.entity.SymptomDiagnoseEntity;
import com.hust.backend.entity.key.SymptomDiagnoseEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomDiagnoseRepository extends JpaRepository<SymptomDiagnoseEntity, SymptomDiagnoseEntityKey> {
    List<SymptomDiagnoseEntity> getAllByDiagnose(DiagnoseEnum diagnoseEnum);
}

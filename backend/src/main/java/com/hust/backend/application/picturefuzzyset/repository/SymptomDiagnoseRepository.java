package com.hust.backend.application.picturefuzzyset.repository;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.entity.SymptomDiagnoseEntity;
import com.hust.backend.application.picturefuzzyset.entity.key.SymptomDiagnoseEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomDiagnoseRepository extends JpaRepository<SymptomDiagnoseEntity, SymptomDiagnoseEntityKey> {
    List<SymptomDiagnoseEntity> getAllByDiagnose(DiagnoseEnum diagnoseEnum);
}

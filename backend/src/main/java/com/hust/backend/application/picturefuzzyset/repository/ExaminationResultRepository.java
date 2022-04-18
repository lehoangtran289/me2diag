package com.hust.backend.application.picturefuzzyset.repository;

import com.hust.backend.application.picturefuzzyset.entity.ExaminationResultEntity;
import com.hust.backend.application.picturefuzzyset.entity.key.ExaminationResultEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationResultRepository extends JpaRepository<ExaminationResultEntity, ExaminationResultEntityKey> {
    List<ExaminationResultEntity> findAllByExaminationId(String id);
}

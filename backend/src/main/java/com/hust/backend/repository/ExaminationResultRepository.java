package com.hust.backend.repository;

import com.hust.backend.entity.ExaminationResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationResultRepository extends JpaRepository<ExaminationResultEntity, String> {
}

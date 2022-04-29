package com.hust.backend.application.KDclassification.repository;

import com.hust.backend.application.KDclassification.entity.KDCExamResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KDCExamResultRepository extends JpaRepository<KDCExamResultEntity, String> {
}

package com.hust.backend.application.picturefuzzyset.repository;

import com.hust.backend.application.picturefuzzyset.entity.ExaminationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<ExaminationEntity, String> {
    List<ExaminationEntity> findAllByPatientId(String patientId);

    Page<ExaminationEntity> findAllByPatientId(String patientId, Pageable pageable);

    Page<ExaminationEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

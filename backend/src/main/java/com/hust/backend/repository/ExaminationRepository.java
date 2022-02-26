package com.hust.backend.repository;

import com.hust.backend.entity.ExaminationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationRepository extends JpaRepository<ExaminationEntity, String> {
}

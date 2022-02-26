package com.hust.backend.repository;

import com.hust.backend.entity.PatientSymptomEntity;
import com.hust.backend.entity.key.PatientSymptomEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientSymptomRepository extends JpaRepository<PatientSymptomEntity, PatientSymptomEntityKey> {
    List<PatientSymptomEntity> findAllByExaminationId(String id);
}

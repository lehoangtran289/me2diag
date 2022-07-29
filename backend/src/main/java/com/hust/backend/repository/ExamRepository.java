package com.hust.backend.repository;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.entity.ExaminationEntity;
import com.hust.backend.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<ExaminationEntity, String> {
    List<ExaminationEntity> findAllByAppIdAndPatientId(ApplicationEnum appId, String patientId);

    Page<ExaminationEntity> findAllByAppIdAndPatientId(ApplicationEnum appId, String patientId, Pageable pageable);

    Page<ExaminationEntity> findAllByAppIdOrderByCreatedAtDesc(ApplicationEnum appId, Pageable pageable);

    Optional<ExaminationEntity> findByIdAndAppId(String id, ApplicationEnum appId);

    List<ExaminationEntity> findAllByPatientId(String patientId);

    void deleteByIdIn(List<String> examIds);

    @Query("SELECT e FROM ExaminationEntity e " +
            "JOIN PatientEntity p ON e.patientId = p.id " +
            "WHERE ((:query IS NULL) " +
            "OR (p.name LIKE CONCAT('%',:query,'%')) " +
            "OR (p.id LIKE CONCAT('%',:query,'%'))) " +
            "AND ((:appId IS NULL) OR (e.appId = :appId)) " +
            "ORDER BY e.createdAt DESC")
    Page<ExaminationEntity> findAllExaminations(@Param("appId") ApplicationEnum appId, @Param("query") String query, Pageable pageable);
}

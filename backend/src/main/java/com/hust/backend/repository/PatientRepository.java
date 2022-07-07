package com.hust.backend.repository;

import com.hust.backend.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String> {
    Page<PatientEntity> findByNameContaining(String query, Pageable pageable);

    Page<PatientEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<PatientEntity> findByNameContainingOrEmailContainingOrPhoneNoContaining(String query, String query1, String query2, Pageable pageable);

}

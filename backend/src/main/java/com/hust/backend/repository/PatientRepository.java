package com.hust.backend.repository;

import com.hust.backend.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String> {
    Page<PatientEntity> findByNameContaining(String query, Pageable pageable);

    Page<PatientEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // find all patients, query by name, email, phoneNo and gender
    @Query("SELECT p FROM PatientEntity p " +
            "WHERE (:query is null " +
            "or (p.name LIKE CONCAT('%',:query,'%') " +
            "or p.email LIKE CONCAT('%',:query,'%') " +
            "or p.phoneNo LIKE CONCAT('%',:query,'%'))) " +
            "and (:gender is null or LOWER(p.gender) = LOWER(:#{#gender}))")
    Page<PatientEntity> findAllPatients(@Param("query") String query, @Param("gender") String gender, Pageable pageable);

    Page<PatientEntity> findByNameContainingOrEmailContainingOrPhoneNoContaining(String query, String query1, String query2, Pageable pageable);

}

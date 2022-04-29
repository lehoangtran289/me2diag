package com.hust.backend.application.picturefuzzyset.repository;

import com.hust.backend.application.picturefuzzyset.entity.PFSExamResultEntity;
import com.hust.backend.application.picturefuzzyset.entity.key.PFSExamResultEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PFSExamResultRepository extends JpaRepository<PFSExamResultEntity, PFSExamResultEntityKey> {
    List<PFSExamResultEntity> findAllByExaminationId(String id);
}

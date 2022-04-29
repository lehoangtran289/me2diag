package com.hust.backend.repository;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, ApplicationEnum> {
}

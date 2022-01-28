package com.hust.backend.repository;

import com.hust.backend.entity.RoleEntity;
import com.hust.backend.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    List<RoleEntity> getAllByIdIn(List<String> roleIds);
}

package com.hust.backend.repository;

import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    List<RoleEntity> getAllByIdIn(List<String> roleIds);

    Optional<RoleEntity> findByRoleEnum(UserRoleEnum roleEnum);
}

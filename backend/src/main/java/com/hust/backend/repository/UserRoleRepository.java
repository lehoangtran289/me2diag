package com.hust.backend.repository;

import com.hust.backend.entity.UserRoleEntity;
import com.hust.backend.entity.key.UserRoleEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntityKey> {

    List<UserRoleEntity> findAllByUserId(String userId);

}

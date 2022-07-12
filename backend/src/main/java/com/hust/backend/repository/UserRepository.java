package com.hust.backend.repository;

import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPasswordToken(String token);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    Page<UserEntity> findByUsernameContainingOrEmailContaining(String username,
                                                               String email,
                                                               Pageable pageable);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN UserRoleEntity ur ON u.id = ur.userId " +
            "JOIN RoleEntity r ON ur.roleId = r.id " +
            "WHERE (:query IS NULL " +
            "OR (u.username LIKE CONCAT('%',:query,'%') " +
            "OR u.email LIKE CONCAT('%',:query,'%') " +
            "OR u.phoneNo LIKE CONCAT('%',:query,'%'))) " +
            "AND (:isEnable IS NULL OR u.isEnable = :isEnable) " +
            "AND ((:roles) IS NULL OR r.roleEnum IN (:roles)) " +
            "GROUP BY u.id")
    Page<UserEntity> findAllUsers(
            @Param("query") String query,
            @Param("roles") List<UserRoleEnum> roles,
            @Param("isEnable") Boolean isEnable,
            Pageable pageable);
}

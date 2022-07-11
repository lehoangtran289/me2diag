package com.hust.backend.repository;

import com.hust.backend.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPasswordToken(String token);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    Page<UserEntity> findByUsernameContainingOrEmailContaining(String username, String email, Pageable pageable);

    @Query("SELECT u FROM UserEntity u " +
            "WHERE (:query is null " +
            "or (u.username LIKE CONCAT('%',:query,'%') " +
            "or u.email LIKE CONCAT('%',:query,'%') " +
            "or u.phoneNo LIKE CONCAT('%',:query,'%'))) " +
            "and (:isEnable is null or u.isEnable = :isEnable)")
    Page<UserEntity> findAllUsers(
            @Param("query") String query,
            @Param("isEnable") Boolean isEnable, Pageable pageable);
}

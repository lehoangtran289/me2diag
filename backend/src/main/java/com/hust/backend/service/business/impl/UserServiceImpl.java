package com.hust.backend.service.business.impl;

import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.UserSignupRequestDTO;
import com.hust.backend.entity.RoleEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.entity.UserRoleEntity;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.repository.RoleRepository;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.repository.UserRoleRepository;
import com.hust.backend.service.business.UserService;
import com.hust.backend.utils.ULID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void registerUser(UserSignupRequestDTO request) {
        if (userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).isPresent()) {
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,
                    "Account already existed!", request.getUsername(), request.getEmail());
        }
        String userId = ULID.nextULID();
        userRepository.save(UserEntity.builder()
                .id(userId)
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isEnable(true)
                .build());
        RoleEntity roleEntity = roleRepository.findByRoleEnum(UserRoleEnum.USER)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Role USER not existed"));
        userRoleRepository.save(UserRoleEntity.builder().userId(userId).roleId(roleEntity.getId()).build());
        log.info("New user registered successfully " + userId);
    }
}

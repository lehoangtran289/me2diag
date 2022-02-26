package com.hust.backend.service.business.impl;

import com.hust.backend.config.AppConfig;
import com.hust.backend.constant.ResourceType;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.dto.request.UserSignupRequestDTO;
import com.hust.backend.dto.response.UserInfoResponseDTO;
import com.hust.backend.entity.RoleEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.entity.UserRoleEntity;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.factory.PagingInfo;
import com.hust.backend.repository.RoleRepository;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.repository.UserRoleRepository;
import com.hust.backend.service.business.UserService;
import com.hust.backend.service.storage.StorageService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import com.hust.backend.utils.ULID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final AppConfig appConfig;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           StorageService storageService,
                           AppConfig appConfig) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.storageService = storageService;
        this.appConfig = appConfig;
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
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Role USER" +
                        " not existed"));
        userRoleRepository.save(UserRoleEntity.builder().userId(userId).roleId(roleEntity.getId()).build());
        log.info("New user registered successfully " + userId);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public UserInfoResponseDTO updateUserInfo(String userId, UserInfoUpdateRequestDTO request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, userId));

        // skip if request's properties are all null
        if (request.isAllPropertiesNull()) {
            return Common.convertObject(user, UserInfoResponseDTO.class);
        }

        // update user info (except avatar)
        Common.copyPropertiesIgnoreNull(request, user, "avatar");

        // update user avatar
        if (request.getAvatar() != null) {
            String relAvatarUrl = storageService.upload(ResourceType.USER, request.getAvatar());
            String absAvatarUrl = appConfig.getDomain() + "/media/" + relAvatarUrl;
            user.setAvatarUrl(absAvatarUrl);
        }

        userRepository.save(user);
        return Common.convertObject(user, UserInfoResponseDTO.class);
    }

    @Override
    public PagingInfo<UserInfoResponseDTO> getAllUsers(String usernameQuery, Pageable pageable) {
        Page<UserEntity> userEntityPage = StringUtils.isBlank(usernameQuery) ?
                userRepository.findAll(pageable) :
                getAllUsersByNameOrEmailLike(usernameQuery, pageable);
        List<UserInfoResponseDTO> results = Transformer.listToList(
                userEntityPage.getContent(),
                userEntity -> Common.convertObject(userEntity, UserInfoResponseDTO.class));
        return PagingInfo.<UserInfoResponseDTO>builder()
                .items(results)
                .currentPage(userEntityPage.getNumber())
                .totalItems(userEntityPage.getTotalElements())
                .totalPages(userEntityPage.getTotalPages())
                .build();
    }

    private Page<UserEntity> getAllUsersByNameOrEmailLike(String query, Pageable pageable) {
        return userRepository.findByUsernameContainingOrEmailContaining(query, query, pageable);
    }

}

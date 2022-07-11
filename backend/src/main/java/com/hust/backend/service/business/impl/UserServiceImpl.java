package com.hust.backend.service.business.impl;

import com.hust.backend.config.AppConfig;
import com.hust.backend.constant.ResourceType;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.dto.request.UserRegisterRequestDTO;
import com.hust.backend.dto.response.UserInfoResponseDTO;
import com.hust.backend.entity.PatientEntity;
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

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final AppConfig appConfig;
    private final Map<String, UserRoleEnum> map = new HashMap<>();

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

    @PostConstruct
    private void postConstruct() {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            RoleEntity roleEntity = roleRepository.findByRoleEnum(role)
                    .orElseThrow(() -> new NotFoundException(UserRoleEnum.class, role.getRole()));
            map.put(roleEntity.getId(), roleEntity.getRoleEnum());
        }
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void registerUser(UserRegisterRequestDTO request) {
        if (userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).isPresent()) {
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,
                    "Account already existed!", request.getUsername(), request.getEmail());
        }
        String userId = ULID.nextULID();
        userRepository.save(UserEntity.builder()
                .id(userId)
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // TODO: client should encode password
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .isEnable(true)
                .build());

        // add user roles
        List<UserRoleEntity> lst = new ArrayList<>();
        for (UserRoleEnum role : request.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByRoleEnum(role)
                    .orElseThrow(() -> new BusinessException(
                            ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Role " + role.name() + " not existed"));
            lst.add(UserRoleEntity.builder().userId(userId).roleId(roleEntity.getId()).build());
        }
        userRoleRepository.saveAll(lst);
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
            if (user.getAvatarUrl() != null) {
                String folder = "/" + ResourceType.USER.folderName + "/";
                try {
                    String avatarFileName = folder + user.getAvatarUrl().split(folder)[1];
                    if (storageService.isExist(avatarFileName)) {
                        storageService.delete(avatarFileName);
                    }
                } catch (Exception ex) {
                    log.error("Error in getting avatarFilename", ex);
                }
            }
            user.setAvatarUrl(absAvatarUrl);
        }
        userRepository.save(user);
        return Common.convertObject(user, UserInfoResponseDTO.class);
    }

    @Override
    public PagingInfo<UserInfoResponseDTO> getAllUsers(
            String usernameQuery,
            List<String> roleStrs,
            Boolean isEnable,
            Pageable pageable
    ) {
        // query to get UserInfoResponseDto without roles
        List<UserRoleEnum> roles = Transformer.listToList(roleStrs, UserRoleEnum::from);
        Page<UserEntity> userEntityPage = userRepository.findAllUsers(usernameQuery, roles, isEnable, pageable);
        List<UserInfoResponseDTO> results = Transformer.listToList(
                userEntityPage.getContent(),
                userEntity -> Common.convertObject(userEntity, UserInfoResponseDTO.class));

        // set corresponding roles to response
        for (UserInfoResponseDTO user : results) {
            List<UserRoleEntity> userRoleEntities = userRoleRepository.findAllByUserId(user.getId());
            user.setRoles(Transformer.listToList(
                    userRoleEntities,
                    urEntity -> map.get(urEntity.getRoleId())
            ));
        }
        return PagingInfo.<UserInfoResponseDTO>builder()
                .items(results)
                .currentPage(userEntityPage.getNumber() + 1)
                .totalItems(userEntityPage.getTotalElements())
                .totalPages(userEntityPage.getTotalPages())
                .build();
    }

    @Override
    public UserInfoResponseDTO getUserInfo(String userId) {
        return Common.convertObject(
                userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException(UserEntity.class, userId)),
                UserInfoResponseDTO.class);
    }

    @Override
    public void deactivateUser(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, userId));
        if (userEntity.isEnable())
            return;
        userEntity.setEnable(false);
        userRepository.save(userEntity);
    }

    private Page<UserEntity> getAllUsersByNameOrEmailLike(String query, Pageable pageable) {
        return userRepository.findByUsernameContainingOrEmailContaining(query, query, pageable);
    }

}

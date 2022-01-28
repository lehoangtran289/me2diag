package com.hust.backend.service.impl;

import com.hust.backend.entity.RoleEntity;
import com.hust.backend.entity.UserRoleEntity;
import com.hust.backend.repository.RoleRepository;
import com.hust.backend.repository.UserRoleRepository;
import com.hust.backend.service.RoleService;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<RoleEntity> getRolesFromUser(String userId) {
        List<String> roleIds = Transformer.listToList(
                userRoleRepository.findAllByUserId(userId),
                UserRoleEntity::getRoleId
        );
        return roleRepository.getAllByIdIn(roleIds);
    }
}

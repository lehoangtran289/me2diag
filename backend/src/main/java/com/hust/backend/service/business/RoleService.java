package com.hust.backend.service.business;

import com.hust.backend.entity.RoleEntity;
import com.hust.backend.entity.UserRoleEntity;

import java.util.List;

public interface RoleService {

    List<RoleEntity> getRolesFromUser(String userId);

}

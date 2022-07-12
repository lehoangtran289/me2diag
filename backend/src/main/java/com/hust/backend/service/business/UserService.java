package com.hust.backend.service.business;

import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.dto.request.UserRegisterRequestDTO;
import com.hust.backend.dto.response.UserInfoResponseDTO;
import com.hust.backend.factory.PagingInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void registerUser(UserRegisterRequestDTO request);

    UserInfoResponseDTO updateUserInfo(String userId, UserInfoUpdateRequestDTO request);

    PagingInfo<UserInfoResponseDTO> getAllUsers(String usernameQuery, List<String> roles, Boolean isEnable, Pageable pageable);

    UserInfoResponseDTO getUserInfo(String userId);

    void deactivateUser(String userId);

    void activateUser(String userId);
}

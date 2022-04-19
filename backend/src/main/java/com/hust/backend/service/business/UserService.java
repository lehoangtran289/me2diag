package com.hust.backend.service.business;

import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.dto.request.UserSignupRequestDTO;
import com.hust.backend.dto.response.UserInfoResponseDTO;
import com.hust.backend.factory.PagingInfo;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void registerUser(UserSignupRequestDTO request);

    UserInfoResponseDTO updateUserInfo(String userId, UserInfoUpdateRequestDTO request);

    PagingInfo<UserInfoResponseDTO> getAllUsers(String usernameQuery, Pageable pageable);

    UserInfoResponseDTO getUserInfo(String userId);
}
